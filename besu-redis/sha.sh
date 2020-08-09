#!/bin/bash
find . -type f -exec sha256sum {} \; &>> SHA256SUMS

