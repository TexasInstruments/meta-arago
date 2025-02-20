FILESEXTRAPATHS:prepend := "${COREBASE}/meta/recipes-extended/ltp/ltp:"
FILESEXTRAPATHS:prepend := "${TITESTBASE}/recipes-extended/ltp/ltp:"

require ltp_${PV}.inc

SUMMARY = "Embedded Linux Device Driver Tests based on Linux Test Project"
HOMEPAGE = "https://git.ti.com/cgit/test-automation/ltp-ddt/"

DEPENDS += "alsa-lib"

PE = "1"
PR = "r1"
PV:append = "+git"

SRCREV = "0b8efc9d4b198420c47be321c236a1c537a433e3"
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
