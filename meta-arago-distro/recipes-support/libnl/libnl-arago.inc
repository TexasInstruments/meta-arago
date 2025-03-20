PR:append = ".arago0"

do_install:append() {
#   Install private files to allow libnl extensions
    install -d ${D}${includedir}/nl-priv-dynamic-core
    cp -r ${S}/include/nl-priv-dynamic-core/cache-api.h ${D}${includedir}/nl-priv-dynamic-core/
    cp -r ${S}/include/nl-priv-dynamic-core/object-api.h ${D}${includedir}/nl-priv-dynamic-core/
    cp -r ${S}/include/nl-priv-dynamic-core/nl-core.h ${D}${includedir}/nl-priv-dynamic-core/
}
