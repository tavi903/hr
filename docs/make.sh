#!/bin/sh
asciidoctor-pdf -r asciidoctor-mathematical index.adoc
ls | grep stem- | xargs rm
