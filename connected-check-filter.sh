awk '
BEGIN {
    IS_START="no"
}

/Started [0-9]+/ {
    IS_START="yes"
}

{
    if (IS_START == "yes") print $0
}

'