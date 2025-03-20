#!/bin/bash

# Get the current project version using Maven
current_version=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
echo "Current version: $current_version"

# Extract the major version and increment it
major=${current_version%%.*}
new_major=$((major + 1))

# Construct the new version string
new_version="${new_major}.0.0"
echo "New version: $new_version"

# Set the new version using Maven
mvn versions:set -DnewVersion=$new_version