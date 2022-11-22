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
_0 dd 0
_1 dd 1
_i@Global dd  ? 
_3 dd 3
@aux4 dd  ? 
_number@Global dd  ? 
_end@Global dd  ? 
_a@Global dd  ? 
@aux3 dd  ? 
@aux2 dd  ? 
@aux1 dd  ? 

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
MOV EAX , _0
MOV _i@Global, EAX
label_4:
MOV EAX, _i@Global
CMP EAX, _end@Global
JGE label_3
MOV EAX, _i@Global
CMP EAX, _number@Global
JNE label_6
MOV EAX , 1
MOV @aux1, EAX
JMP label_3
label_6:
label_5:
MOV EAX, _i@Global
ADD EAX, _null
MOV @aux4, EAX
MOV EAX , @aux4
MOV _i@Global, EAX
JMP label_4
label_3:
label_2:
MOV EAX , 3
MOV @aux1, EAX
label_1:
MOV EAX , @aux1
MOV _a@Global, EAX
invoke ExitProcess, 0 
end main