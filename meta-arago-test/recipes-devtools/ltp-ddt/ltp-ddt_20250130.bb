require ltp_${PV}.inc

SUMMARY = "Embedded Linux Device Driver Tests based on Linux Test Project"
HOMEPAGE = "https://git.ti.com/cgit/test-automation/ltp-ddt/"

DEPENDS += "alsa-lib"

PE = "1"
PR = "r1"
PV:append = "+git"

SRCREV = "d23395da4e67855a7d7ce476e44f79b8e604998e"
BRANCH ?= "master"

SRC_URI:remove = "git://github.com/linux-test-project/ltp.git;branch=master;protocol=https"
SRC_URI:prepend = "git://git.ti.com/git/test-automation/ltp-ddt.git;protocol=https;branch=${BRANCH} "

SRC_URI += "file://0001-listmount04-Update-for-6.18-kernel-headers.patch"

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
