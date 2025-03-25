#!/bin/bash

PACKAGE_NAME=graph.core

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
    sed -i "s/^${PACKAGE_NAME}=.*/${PACKAGE_NAME}=${new_version}/" /var/go/versions.properties
else
    echo "${PACKAGE_NAME}=${new_version}" >> /var/go/versions.properties
fi