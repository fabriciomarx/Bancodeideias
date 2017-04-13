

//Função para validar campos. Se um campo for requerido, nao apagar os outros campos
function verificar(args, dlg) {
    if (args.validationFailed)
        PF(dlg).jq.effect("shake", {times: 5}, 100);
    else {
        PF(dlg).hide(); //ocultar o dialogo
    }


}

//PF serve para chamar um componente do primefaces atravez do seu widgetVar
//efeito shake é choacalhar na tela, 5 vezes no intervalo de 100 milisegundos