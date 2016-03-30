awk '
BEGIN {
    IS_START="no"
}

/Starting [0-9]+/ {
    IS_START="yes"
}

{
    if (IS_START == "yes") print $0
}

'
