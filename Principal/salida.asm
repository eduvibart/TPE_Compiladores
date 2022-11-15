.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\masm32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\masm32.lib
include \masm32\include\user32.inc
includelib \masm32\lib\user32.lib

.data
_a@Global dd  ?
_2_3F10 dd 4142.651121364896
_b@Global dd  ?
_3_40282347F38 dd 1.6214290267397715E20
@aux1 dd  ?

.code
main:
FLD _3_40282347F38
FST _a@Global
FLD _2_3F10
FST _b@Global
FLD _b@Global
FADD _a@Global
FST @aux1
FLD @aux1
FST _a@Global
end main