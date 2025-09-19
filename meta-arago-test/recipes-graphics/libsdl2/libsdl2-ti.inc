EXTRA_OECMAKE:append = " -DSDL_TESTS=ON \
                         -DSDL_INSTALL_TESTS=ON \
                       "
PACKAGE_BEFORE_PN = "${PN}-tests"
FILES:${PN}-tests += "${libexecdir} ${datadir}/installed-tests/SDL2"
FILES:${PN}-tests:class-nativesdk += "${libexecdir} ${datadir}/installed-tests/SDL2"
