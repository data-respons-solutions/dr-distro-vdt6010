DESCRIPTION = "vdt6010 reference image"

require recipes-bsp/images/datarespons-image.bb

FEATURE_PACKAGES_vdt6010-apps = "\
	packagegroup-vdt6010-browser \
	packagegroup-vdt6010-base \
"

IMAGE_FEATURES += "vdt6010-apps"

ROOTFS_POSTPROCESS_COMMAND_append = " add_mountpoints;"

add_mountpoints() {
	install -d ${IMAGE_ROOTFS}/opt
	install -d ${IMAGE_ROOTFS}/opt/app
	install -d ${IMAGE_ROOTFS}/opt/data
	echo "PARTLABEL=app      /opt/app        ext4       defaults,ro,nofail           0  0" >> ${IMAGE_ROOTFS}/etc/fstab
	echo "PARTLABEL=data     /opt/data       ext4       defaults,rw,nofail           0  0" >> ${IMAGE_ROOTFS}/etc/fstab
}
