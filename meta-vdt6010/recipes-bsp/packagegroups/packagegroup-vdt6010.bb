SUMMARY = "Standard packages for vdt6010"
LICENSE = "MIT"

inherit packagegroup

PACKAGE_ARCH = "${MACHINE_ARCH}"

PROVIDES = "${PACKAGES}"
PACKAGES = "\
	packagegroup-vdt6010-base \
	packagegroup-vdt6010-browser \
"

RDEPENDS_packagegroup-vdt6010-base = "\
	swap-root \
	image-install \
	backlightctl \
"

RDEPENDS_packagegroup-vdt6010-browser = "\
	chromium-ozone-wayland \
"
