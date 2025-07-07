#!/bin/bash

# Exit on error
set -e

# Check if required commands are installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven (mvn) is not installed."
    exit 1
fi

if ! command -v gh &> /dev/null; then
    echo "Error: GitHub CLI (gh) is not installed."
    exit 1
fi

# Get the version from Maven
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

# Ensure version is not empty
if [[ -z "$VERSION" ]]; then
    echo "Error: Failed to retrieve project version."
    exit 1
fi

# Check if the release already exists on GitHub
if gh release view "$VERSION" &> /dev/null; then
    echo "Release $VERSION already exists. Deleting..."
    gh release delete "$VERSION" --yes
fi

# Check if the tag exists locally
if git rev-parse "$VERSION" >/dev/null 2>&1; then
    echo "Tag $VERSION exists locally."

    # Check if the tag exists on GitHub
    if git ls-remote --tags origin | grep -q "refs/tags/$VERSION"; then
        echo "Tag $VERSION exists on GitHub. Deleting..."
        git push --delete origin "$VERSION"
    fi

    echo "Recreating tag $VERSION..."
    git tag -d "$VERSION"
fi

# Create and push the tag again
git tag "$VERSION"
git push origin "$VERSION"

# Create a new release
gh release create "$VERSION" --title "Version $VERSION" --notes "New Version Release"

echo "GitHub release $VERSION created successfully!"

