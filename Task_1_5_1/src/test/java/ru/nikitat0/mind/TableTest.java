package ru.nikitat0.mind;

import org.junit.jupiter.api.Test;

import ru.nikitat0.mind.Table.Alignment;

class TableTest {
    @Test
    void testTable() {
        Table table = new Table.Builder(new Text("Before"), new Text("After"))
                .setAlignment(Alignment.Center, Alignment.Center)
                .addRow(new Text("italic"), new Text.Italic("italic"))
                .addRow(new Text("bold"), new Text.Bold("bold"))
                .build();
        MdTest.assertMd(MdTest.multiline(
                "| Before |  After   |",
                "| :----: | :------: |",
                "| italic | _italic_ |",
                "|  bold  | **bold** |"), table);
    }
}
