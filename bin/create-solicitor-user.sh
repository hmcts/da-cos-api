#!/usr/bin/env bash

set -eu

PASSWORD=${1}
USER_EMAIL="${2:-solicitor@server.net}"
ENV="${3:-local}"
FORENAME="${4:-John}"
SURNAME="${5:-Smith}"
USER_ROLES='[{"code":"caseworker"},{"code":"caseworker-domesticabuse"},{"code":"caseworker-domesticabuse-solicitor"}]'

binFolder=$(dirname "$0")

${binFolder}/create-user.sh "${USER_EMAIL}" "${ENV}" "${FORENAME}" "${SURNAME}" "${PASSWORD}" "${USER_ROLES}"
