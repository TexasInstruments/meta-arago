SUMMARY = "Add a uEnv.txt file into the deploy directory"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "\
    file://uEnv.txt \
"

PR = "r4"
PV = "1.0"

S = "${UNPACKDIR}"

inherit nopackages

# deploy files for wic image
inherit deploy
do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${S}/uEnv.txt ${DEPLOYDIR}
}
addtask deploy before do_build after do_unpack
