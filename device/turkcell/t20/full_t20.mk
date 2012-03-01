$(call inherit-product, $(SRC_TARGET_DIR)/product/languages_small.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/generic.mk)

# Discard inherited values and use our own instead.
PRODUCT_NAME := full_t20
PRODUCT_DEVICE := t20
PRODUCT_MODEL := Full Turkcell T20 Image for Emulator
PRODUCT_LOCALES += tr_TR

include $(call all-makefiles-under,$(LOCAL_PATH))