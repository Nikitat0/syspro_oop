package ru.nikitat0.mind;

import static ru.nikitat0.mind.MdTest.DUMMY_ELEMENT;
import static ru.nikitat0.mind.MdTest.EMPTY_ELEMENT;

import org.junit.jupiter.api.Assertions;
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

    @Test
    void testEmptyTable() {
        Table table = new Table.Builder(EMPTY_ELEMENT, EMPTY_ELEMENT, EMPTY_ELEMENT, EMPTY_ELEMENT)
                .setAlignment(Alignment.Unspecified, Alignment.Left, Alignment.Center, Alignment.Right)
                .build();
        MdTest.assertMd(MdTest.multiline(
                "|     |     |     |     |",
                "| --- | :-- | :-: | --: |"), table);
    }

    @Test
    void testTableBuilder() {
        Table.Builder builder = new Table.Builder(new Text("Hello"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> builder.setAlignment());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> builder.setAlignment(Alignment.Left, Alignment.Right));

        Assertions.assertThrows(IllegalArgumentException.class, () -> builder.addRow());
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> builder.addRow(DUMMY_ELEMENT, DUMMY_ELEMENT));
    }
}
