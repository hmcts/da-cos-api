#!/usr/bin/env bash

scriptPath=$(dirname $(realpath $0))

# Roles used during the CCD import
${scriptPath}/add-ccd-role.sh "caseworker-domesticabuse"
${scriptPath}/add-ccd-role.sh "caseworker-domesticabuse-caseworker"
${scriptPath}/add-ccd-role.sh "caseworker-domesticabuse-superuser"
${scriptPath}/add-ccd-role.sh "caseworker-domesticabuse-la"
${scriptPath}/add-ccd-role.sh "caseworker-domesticabuse-judge"
${scriptPath}/add-ccd-role.sh "caseworker-domesticabuse-courtadmin"
${scriptPath}/add-ccd-role.sh "caseworker-domesticabuse-magistrate"
${scriptPath}/add-ccd-role.sh "caseworker-domesticabuse-barrister"
${scriptPath}/add-ccd-role.sh "caseworker-domesticabuse-solicitor"
${scriptPath}/add-ccd-role.sh "caseworker-domesticabuse-systemupdate"
