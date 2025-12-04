FILESEXTRAPATHS:prepend := "${COREBASE}/meta/recipes-extended/ltp/ltp:"
FILESEXTRAPATHS:prepend := "${TITESTBASE}/recipes-extended/ltp/ltp:"

require ltp_${PV}.inc

SUMMARY = "Embedded Linux Device Driver Tests based on Linux Test Project"
HOMEPAGE = "https://git.ti.com/cgit/test-automation/ltp-ddt/"

DEPENDS += "alsa-lib"

PE = "1"
PR = "r1"
PV:append = "+git"

SRCREV = "d6180b2718b632cb8de3be30df98c33f9cad87a6"
BRANCH ?= "master"

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
    libgpiod-tools \
"

do_install:prepend() {
	# Upstream ltp recipe wants to remove this test case in do_install
	install -d ${D}${prefix}/runtest/
	echo "memcg_stress" >> ${D}${prefix}/runtest/controllers
}
