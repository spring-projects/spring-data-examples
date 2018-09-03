#!/usr/bin/env bash

MVN_ARGS=""

cd spring-data-examples

if [ ! -z ${PROFILE+x} ] ; then
  MVN_ARGS="${MVN_ARGS} -P${PROFILE}"
fi

if [ ! -z ${BOOT+x} ] ; then

    echo "mvn versions:update-parent -DgenerateBackupPoms=false -DallowSnapshots=true -DparentVersion=\"[${BOOT}]\""
    echo

    mvn versions:update-parent -DgenerateBackupPoms=false -DallowSnapshots=true -DparentVersion="[${BOOT}]"
fi

echo "Running: mvn clean dependency:list test -Dsort -Dmaven.test.redirectTestOutputToFile=true -B ${MVN_ARGS}"
echo
mvn clean test -Dsort -Dmaven.test.redirectTestOutputToFile=true -B ${MVN_ARGS}
