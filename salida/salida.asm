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
_d@Global dd  ? 
_b@Global dd  ? 

.code
main:
MOV EAX , _a@Global
MOV _c@Global, EAX
MOV EAX , _c@Global
MOV _d@Global, EAX
end main