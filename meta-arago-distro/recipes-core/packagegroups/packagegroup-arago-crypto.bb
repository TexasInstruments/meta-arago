SUMMARY = "Task to install crypto packages into target FS"
LICENSE = "MIT"
PR = "r13"

inherit packagegroup

CRYPTO_SUPPORT = "\
    openssl \
    openssl-bin \
    openssl-conf \
    openssl-engines \
    cryptodev-module \
    opensc \
"

RDEPENDS:${PN} = "\
    ${CRYPTO_SUPPORT} \
"
