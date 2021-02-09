fin-id-utis - validate Finnish personal identity codes (HETU and SATU)
======================================================================

## Usage

Validate a personal identity code.

    HetuUtil.isValid("131052+308T")

Check the gender of an identity code.

    HetuUtil.isFemale("131052-308T")

Generate random personal identity code.

    HetuUtil.generateRandom()
    
Compute the control character suffix for a personal idenity code.

    HetuUtil.computeControlCharacter("131052-308")

![CI](https://github.com/mpolla/fin-id-utils/workflows/CI/badge.svg)

