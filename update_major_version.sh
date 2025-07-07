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

# Load versions to be used
# shellcheck source="${VERSIONS_FILE_PATH}"
source "${VERSIONS_FILE_PATH}"

#add parents version and current version
xmlstarlet ed -L \
  -u "//_:project/_:parent/_:version" -v "${REACTOME_PARENT_VERSION}" \
  -u "//_:project/_:version" -v "${!PACKAGE_NAME}" \
   "pom.xml"

echo "Maven ${PACKAGE_NAME} updated to ${!PACKAGE_NAME}"
echo "Maven parent updated to ${REACTOME_PARENT_VERSION}"


# deleting custom versions of org.reactome.server
xmlstarlet ed -L -d "//_:dependency[_:groupId[starts-with(text(),'org.reactome')]]/_:version" pom.xml
