package pt.upskill.projeto1.gui;

public class Story {

    public static void mensagemInicial() {
        String string = "Dizem que nas profundezas destas ruínas existe um\n" +
                "livro que em tempos pertenceu a um grande feiticeiro.\n" +
                "Dizem que nesse livro estão registados os maiores\n" +
                "feitiços e encantamentos que alguma vez foram criados\n" +
                "e que foram perdidos depois dos praticantes de Magia\n" +
                "terem sido expulsos destas terras.\n" +
                "O teu objetivo é encontrar este livro.\n";
        ImageMatrixGUI.getInstance().showMessage("", string);
    }

    public static String mensagemFinalWin() {
        return " Finalmente encontraste o livro lendário depois de atravessar\n" +
                "salas infestadas de criaturas malévolas e armadilhas traiçoeiras.\n" +
                "Fama, fortuna e poder inimaginável estão ao teu alcance. Mas\n" +
                "assim que abres o livro, as páginas desfazem-se em pó. Parece\n" +
                "que estar guardado numa masmorra húmida durante centenas de\n" +
                "anos não é bom para conservar livros.\n\n";
    }

    public static String mensagemFinalLose() {
        return "Infelizmente não tiveste capacidade suficiente para\n" +
                "derrotar os inimigos e encontrar o tesouro escondido\n" +
                "nestas ruínas. Vais juntar-te aos esqueletos que vagueiam\n" +
                "por estas salas, até que venha outra pessoa que finalmente\n" +
                "liberte o teu espírito desta prisão.\n\n";
    }
}
