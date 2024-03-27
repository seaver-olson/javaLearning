message=""
if [ $# -eq 0 ]; then
    message="No message added"
else
    message="$1"
fi

cd FTP || exit 1
rm Admin/*.class
rm Client/*.class

git add .
git commit -m "$message"
git push
