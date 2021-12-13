#!/usr/bin/env bash

scriptPath=$(dirname $(realpath $0))

# Roles used during the CCD import
${scriptPath}/add-ccd-role.sh "caseworker-da"
${scriptPath}/add-ccd-role.sh "caseworker-da-caseworker"
${scriptPath}/add-ccd-role.sh "caseworker-da-superuser"
${scriptPath}/add-ccd-role.sh "caseworker-da-la"
${scriptPath}/add-ccd-role.sh "caseworker-da-judge"
${scriptPath}/add-ccd-role.sh "caseworker-da-courtadmin"
${scriptPath}/add-ccd-role.sh "caseworker-da-magistrate"
${scriptPath}/add-ccd-role.sh "caseworker-da-barrister"
${scriptPath}/add-ccd-role.sh "caseworker-da-solicitor"
${scriptPath}/add-ccd-role.sh "caseworker-da-systemupdate"
