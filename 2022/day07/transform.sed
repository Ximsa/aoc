#!/usr/bin/sed -f
s/[0-9]\{1,\}/truncate -s &/
s/$ //
s/ls//
s/dir/mkdir/
