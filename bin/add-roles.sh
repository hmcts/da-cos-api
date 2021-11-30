#!/usr/bin/env bash

scriptPath=$(dirname $(realpath $0))

# Roles used during the CCD import
${scriptPath}/add-ccd-role.sh "caseworker-da-courtadmin_beta"
${scriptPath}/add-ccd-role.sh "caseworker-da-superuser"
${scriptPath}/add-ccd-role.sh "caseworker-da-courtadmin-la"
${scriptPath}/add-ccd-role.sh "caseworker-da-courtadmin"
${scriptPath}/add-ccd-role.sh "caseworker-da-solicitor"
${scriptPath}/add-ccd-role.sh "caseworker-da-pcqextractor"
${scriptPath}/add-ccd-role.sh "caseworker-da-systemupdate"
${scriptPath}/add-ccd-role.sh "caseworker-da-bulkscan"
