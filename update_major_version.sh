#!/bin/bash

PACKAGE_NAME=GRAPH_CORE_VERSION

# Check if VERSIONS_FILE_PATH is not set or empty
if [ -z "${VERSIONS_FILE_PATH}" ]; then
    echo "Error: Please define \${VERSIONS_FILE_PATH} in the GO-CD environment or in the server."
    exit 1
fi

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

if grep -q "^${PACKAGE_NAME}=" /var/go/versions.properties; then
    sed -i "s/^${PACKAGE_NAME}=.*/${PACKAGE_NAME}=${new_version}/" "${VERSIONS_FILE_PATH}"
else
    echo "${PACKAGE_NAME}=${new_version}" >> "${VERSIONS_FILE_PATH}"
fi
