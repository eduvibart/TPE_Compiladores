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
outMens db "Out", 0 
error db "Error de ejecucion!!!", 0 
@tagAnt dd ? 
_1 dd 1
@aux5 dd  ? 
@aux4 dd  ? 
_5 dd 5
_a1@Global dd  ? 
_a2@Global dd  ? 
@aux3 dd  ? 
@aux2 dd  ? 
@aux1 dd  ? 
_10 dd 10

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
main:
outer:
label_1:
MOV EAX, _a1@Global
CMP EAX, _10
JGE label_2
label_4:
MOV EAX, _a2@Global
CMP EAX, _5
JGE label_5
MOV EAX, _a1@Global
ADD EAX, _1
MOV @aux3, EAX
MOV EAX , @aux3
MOV _a1@Global, EAX
JMP outer
label_6:
MOV EAX, _a2@Global
ADD EAX, _1
MOV @aux4, EAX
MOV EAX , @aux4
MOV _a2@Global, EAX
JMP label_4
label_5:
label_3:
MOV EAX, _a1@Global
ADD EAX, _1
MOV @aux5, EAX
MOV EAX , @aux5
MOV _a1@Global, EAX
JMP label_1
label_2:
invoke ExitProcess, 0 
end main