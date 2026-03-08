@ECHO OFF

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM delete saved data to ensure a clean test run
if exist ..\data\goofy.txt del ..\data\goofy.txt
if exist data\goofy.txt del data\goofy.txt

REM run the program using IntelliJ compiled output
java -classpath ..\out\production\ip goofy.Goofy < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT
if %ERRORLEVEL% == 0 (
    echo Test result: PASSED
) else (
    echo Test result: FAILED
)