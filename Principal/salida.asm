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
_c@Global dd  ?
_a@Global dd  ?
_b@Global dd  ?
@aux1 dd  ?
_3 dd 3

.code
main:
MOV EAX , _3
MOV _a@Global, EAX
MOV EAX , _3
MOV _b@Global, EAX
MOV EAX, _a@Global
ADD EAX, _b@Global
MOV @aux1, EAX
MOV EAX , @aux1
MOV _a@Global, EAX
end main