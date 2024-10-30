package pt.upskill.projeto1.gui;

public class Story {

    public static void mensagemInicial() {
        String string = "Dizem que nas profundezas destas ruínas existe um livro\n" +
                " que em tempos pertenceu a um grande feiticeiro.\n" +
                "Dizem que nesse livro estão registados os maiores\n" +
                " feitiços e encantamentos que alguma vez foram criados e que\n" +
                "foram perdidos depois dos praticantes de Magia terem sido\n" +
                " expulsos destas terras.\n" +
                "O teu objetivo é encontrar este livro.";
        ImageMatrixGUI.getInstance().showMessage("", string);
    }

    public static String mensagemFinal() {
        return "Finalmente encontraste o livro lendário depois \n" +
                "de atravessar salas infestadas de criaturas malévolas e \n" +
                "armadilhas traiçoeiras. Fama, fortuna e poder inimaginável \n" +
                "estão ao teu alcance. Mas assim que abres o livro, as páginas \n" +
                "desfazem-se em pó. Parece que estar guardado numa masmorra \n" +
                "húmida durante centenas de anos não é bom para conservar livros.\n";
    }
}
