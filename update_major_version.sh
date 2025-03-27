#!/bin/bash

PACKAGE_NAME="GRAPH_CORE_VERSION"

# Check if VERSIONS_FILE_PATH is set
if [ -z "${VERSIONS_FILE_PATH}" ]; then
    echo "Error: Please define \${VERSIONS_FILE_PATH} in the GO-CD environment or in the server."
    exit 1
fi

# Check if VERSIONS_FILE_PATH exists
if [ ! -f "${VERSIONS_FILE_PATH}" ]; then
    echo "Error: File ${VERSIONS_FILE_PATH} not found."
    exit 1
fi

# Extract version from the file
if grep -q "^${PACKAGE_NAME}=" "${VERSIONS_FILE_PATH}"; then
    current_version=$(grep "^${PACKAGE_NAME}=" "${VERSIONS_FILE_PATH}" | cut -d'=' -f2)
else
    echo "Error: ${PACKAGE_NAME} not found in ${VERSIONS_FILE_PATH}"
    exit 1
fi

echo "Version read from ${VERSIONS_FILE_PATH}: ${current_version}"

# Set the extracted version in Maven
mvn versions:set -DnewVersion="${current_version}"

echo "Maven version updated to ${current_version}"
