package com.Management_Risk_PFE.enums;

import java.util.EnumSet;
import java.util.Set;

public enum AssetType {
    POEPLE,
    INFORMATION,
    HARDWARE,
    SERVERS,
    APPLICATION,
    SUPPLIERS,
    COMPANY_IMAGE,

    HARDWARE_LAPTOP,

    HARDWARE_NETWORK,

    HARDWARE_BARCODE_SCANNER,

    HARDWARE_TV,
    HARDWARE_PRINTERS,

    HARDWARE_PHONE,

    HARDWARE_STOCK,

    HARDWARE_USB,

    HARDWARE_CCTV;

    public static Set<AssetType> allPermissions() {
        return EnumSet.allOf(AssetType.class);
    }
}
