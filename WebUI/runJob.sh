#!/bin/bash

# Set the $HADOOP_HOME with your hadoop directory
# Set the $JOB with your job name

#JOB="InvertedIndex.jar"

#INPUT_FILE_HOME="/home/bobiw_000/workspace/CreateInverted/output/part-00000"
INPUT_FILE_HOME="/home/bobiw_000/workspace/CreateInverted/output/part-00000"

#XML_FILE="/home/bobiw_000/workspace/CreateInverted/input/2GSample"
XML_FILE="/home/bobiw_000/workspace/CreateInverted/input/2GSample"

#InvertedIndexJob="QueryInvertedIndex.jar"
InvertedIndexJob="QueryInvertedIndex.jar"

#GetXMLJob="GetXML.jar"
GetXML="GetXML.jar"

DUMPOUTPUTXML="/home/bobiw_000/OutXML"
OUTPUTDOCIDS="/home/bobiw_000/OutputDOCIDS/"
TARGETOUTPUTXML="/home/bobiw_000/public_html/ResultXML"

PART="/part-00000"

cd /home/bobiw_000/Desktop

/home/bobiw_000/Hadoop/hadoop-1.0.3/bin/hadoop jar $InvertedIndexJob $INPUT_FILE_HOME $OUTPUTDOCIDS "$1";
/home/bobiw_000/Hadoop/hadoop-1.0.3/bin/hadoop jar $GetXML GetXMLConfig $XML_FILE $DUMPOUTPUTXML "$OUTPUTDOCIDS$PART";

cp -f $DUMPOUTPUTXML$PART $TARGETOUTPUTXML
rm -rf $DUMPOUTPUTXML/
rm -rf $OUTPUTDOCIDS

