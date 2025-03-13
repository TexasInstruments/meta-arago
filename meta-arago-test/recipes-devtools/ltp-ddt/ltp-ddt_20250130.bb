FILESEXTRAPATHS:prepend := "${COREBASE}/meta/recipes-extended/ltp/ltp:"
FILESEXTRAPATHS:prepend := "${TITESTBASE}/recipes-extended/ltp/ltp:"

require ltp_${PV}.inc

SUMMARY = "Embedded Linux Device Driver Tests based on Linux Test Project"
HOMEPAGE = "https://git.ti.com/cgit/test-automation/ltp-ddt/"

DEPENDS += "alsa-lib"

PE = "1"
PR = "r1"
PV:append = "+git"

SRCREV = "e6f5e9cb24d38b209e8ced9ddef08f176b28fdc8"
BRANCH ?= "cicd"

SRC_URI:remove = "git://github.com/linux-test-project/ltp.git;branch=master;protocol=https"
SRC_URI:prepend = "git://git.ti.com/git/test-automation/ltp-ddt.git;protocol=https;branch=${BRANCH} "

export prefix = "/opt/ltp"
export exec_prefix = "/opt/ltp"

EXTRA_OEMAKE:append = " \
    KERNEL_USR_INC=${WORKDIR} \
    ALSA_INCPATH=${STAGING_INCDIR} \
    ALSA_LIBPATH=${STAGING_LIBDIR} \
"

RDEPENDS:${PN} += "\
    acl \
    at \
    pm-qa \
    serialcheck \
    memtester \
"

do_install:prepend() {
	# Upstream ltp recipe wants to remove this test case in do_install
	install -d ${D}${prefix}/runtest/
	echo "memcg_stress" >> ${D}${prefix}/runtest/controllers
}
