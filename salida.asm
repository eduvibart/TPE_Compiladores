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
errorMensFun db "El camino tomado por la funcion no tiene retorno.", 0 
errorMensDivisionPorCero db "No se puede dividir por cero.", 0 
errorMensProductoEnteros db "Se produjo un overflow en el producto de enteros.", 0 
errorMensRecursionMutua db "Se produjo un llamado recursivo mutuo.", 0 
error db "Error de ejecucion!!!", 0 
@tagAnt dd ? 
@aux6 dd  ? 
@aux5 dd  ? 
_69 dd 69
@aux4 dd  ? 
@aux3 dd  ? 
@aux2 dd  ? 
@aux1 dd  ? 
@aux@h@Global dd  ? 
_78 dd 78
@aux@f1@Global@f dd  ? 
@aux@f2@Global@f@f1 dd  ? 
_a@Global dd  ? 
@aux@g@Global dd  ? 
@aux@f@Global dd  ? 
_b@Global dd  ? 
_c@Global dd  ? 
_1 dd 1
_2 dd 2

.code
errorFun: 
invoke MessageBox, NULL, addr errorMensFun, addr error, MB_OK 
invoke ExitProcess, 1 
errorDivisionPorCero: 
invoke MessageBox, NULL, addr errorMensDivisionPorCero, addr error, MB_OK 
invoke ExitProcess, 1 
errorProductoEnteros: 
invoke MessageBox, NULL, addr errorMensProductoEnteros, addr error, MB_OK 
invoke ExitProcess, 1 
errorRecursionMutua: 
invoke MessageBox, NULL, addr errorMensRecursionMutua, addr error, MB_OK 
invoke ExitProcess, 1 
h@Global:
MOV EAX, _69
MOV @aux@h@Global, EAX
ret 
JMP errorFun
g@Global:
MOV EAX, @tagAnt 
CMP EAX, h@Global
JE errorRecursionMutua 
MOV EAX, g@Global
MOV @tagAnt, EAX 
call h@Global
MOV EAX, @aux@h@Global
MOV @aux1, EAX
XOR EAX, EAX 
MOV @tagAnt, EAX 
MOV EAX, _78
MOV @aux@g@Global, EAX
ret 
JMP errorFun
f2@Global@f@f1:
MOV EAX, @tagAnt 
CMP EAX, f1@Global@f
JE errorRecursionMutua 
MOV EAX, f2@Global@f@f1
MOV @tagAnt, EAX 
call f1@Global@f
MOV EAX, @aux@f1@Global@f
MOV @aux2, EAX
XOR EAX, EAX 
MOV @tagAnt, EAX 
MOV EAX, _1
MOV @aux@f2@Global@f@f1, EAX
ret 
JMP errorFun
f1@Global@f:
MOV EAX, @tagAnt 
CMP EAX, f@Global
JE errorRecursionMutua 
MOV EAX, f1@Global@f
MOV @tagAnt, EAX 
call f@Global
MOV EAX, @aux@f@Global
MOV @aux3, EAX
XOR EAX, EAX 
MOV @tagAnt, EAX 
MOV EAX, @tagAnt 
CMP EAX, f2@Global@f@f1
JE errorRecursionMutua 
MOV EAX, f1@Global@f
MOV @tagAnt, EAX 
call f2@Global@f@f1
MOV EAX, @aux@f2@Global@f@f1
MOV @aux4, EAX
XOR EAX, EAX 
MOV @tagAnt, EAX 
MOV EAX, _1
MOV @aux@f1@Global@f, EAX
ret 
JMP errorFun
f@Global:
MOV EAX, @tagAnt 
CMP EAX, g@Global
JE errorRecursionMutua 
MOV EAX, f@Global
MOV @tagAnt, EAX 
call g@Global
MOV EAX, @aux@g@Global
MOV @aux5, EAX
XOR EAX, EAX 
MOV @tagAnt, EAX 
MOV EAX, _1
MOV @aux@f@Global, EAX
ret 
JMP errorFun
main:
MOV EAX , _1
MOV _a@Global, EAX
MOV EAX , _2
MOV _b@Global, EAX
call f@Global
MOV EAX, @aux@f@Global
MOV @aux6, EAX
invoke ExitProcess, 0 
end main