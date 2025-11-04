SUMMARY = "Application for routing native applications via websockets"
HOMEPAGE = "http://websocketd.com/"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=a14d7befdbee1290ac5c472cd85d66f2"

S = "${WORKDIR}"

SRC_URI = "\
    git://${GO_IMPORT}.git;protocol=https;branch=master;destsuffix=src/${GO_IMPORT} \
    git://${GO_IMPORT_websocket}.git;protocol=https;branch=main;name=websocket;destsuffix=src/${GO_IMPORT_websocket} \
    file://0001-examples-Move-to-python3.patch \
"

SRCREV = "035c18cc3e6962dabd5ea2ad8845260726a4a99e"
SRCREV_websocket = "66b9c49e59c6c48f0ffce28c2d8b8a5678502c6d"

SRCREV_FORMAT .= "_websocket"

GO_IMPORT = "github.com/joewalnes/websocketd"
GO_IMPORT_websocket = "github.com/gorilla/websocket"

export GO111MODULE = "off"
inherit go

RDEPENDS:${PN}-dev = "bash perl php-cli python3-core ruby"
