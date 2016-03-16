#!/bin/sh

WWW_DIR="$HOME/www"

if [ ! -d "$WWW_DIR" ]; then
   echo 'I cannot find a www directory, are you on the school servers?'
   exit 1
fi

echo 'This script will generate javadoc for all java fies in this directory and any subdirectories.'
echo -n "This script will DELETE any existing files in your www directory ($WWW_DIR). Are you SURE? [y/n]: "
read sure

if [ "$sure" != "y" -a "$sure" != "n" ]; then
   echo "I don't understand."
   exit 2
fi

if [ `find . -type f -iname '*.java' | wc -l` -eq 0 ]; then
   echo "I can't find any java files in this directory... Nothing done."
   exit 3
fi

rm -Rf "$WWW_DIR/*"
if [ $? -ne 0 ]; then
   echo 'Unable to delete the current files in your www directory. Please check this out.'
   exit 4
fi

find . -type f -iname '*.java' | xargs javadoc -d "$WWW_DIR"
if [ $? -ne 0 ]; then
   echo 'Unable to generate javadocs. See output for details.'
   exit 5
fi

find "$WWW_DIR" -type d -exec chmod 775 {} \;
find "$WWW_DIR" -type f -exec chmod 664 {} \;
