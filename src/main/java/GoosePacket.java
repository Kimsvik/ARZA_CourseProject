import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@Setter @Getter
public class GoosePacket {
    /* Байты префиксы атрибутов */
    private final byte[] attributeForGoosePdu = {0x61, (byte) 0x81, (byte) 0x8A};
    private final byte[] attributeForGoCBRef = {(byte) 0x80, 0x25};
    private final byte[] attributeForTimeAllowedToLive = {(byte) 0x81, 0x02};
    private final byte[] attributeForDatSet = {(byte) 0x82, 0x1C};
    private final byte[] attributeForGoID = {(byte) 0x83, 0x07};
    private final byte[] attributeForT = {(byte) 0x84, 0x08};
    private final byte[] attributeForStNum = {(byte) 0x85, 0x01};
    private final byte[] attributeForSqNum = {(byte) 0x86, 0x03};
    private final byte[] attributeForSimulation = {(byte) 0x87, 0x01};
    private final byte[] attributeForConfRev = {(byte) 0x88, 0x01};
    private final byte[] attributeForNdsCom = {(byte) 0x89, 0x01};
    private final byte[] attributeForNumDatSetEntries = {(byte) 0x8A, 0x01};
    private final byte[] attributeForAllDate = {(byte) 0xAB, 0x18};
    private final byte[] attributeForBitString = {(byte) 0x84, 0x03};
    private final byte[] attributeForBoolean = {(byte) 0x83, 0x01};

    /* Атрибуты данных */
    private final byte[] destination = {0x01, 0x0C, (byte) 0xCD, 0x01, 0x01, (byte) 0xFF};
    //private final byte[] destination = {0x01, 0x0C, (byte) 0xCD, 0x04, 0x00, 0x01};
    private final byte[] source = {(byte)0xD8, (byte) 0xF8, (byte) 0x83, (byte) 0xF7, (byte) 0x96, (byte) 0x26};
    private final byte[] type = {(byte) 0x88, (byte) 0xB8};
    private final byte[] appID = {0x00, 0x03};
    private final byte[] length = {0x00, (byte) 0x95};
    private final byte[] reserved1 = {0x00, 0x00};
    private final byte[] reserved2 = {0x00, 0x00};

    /* Ссылка блока управления GOOSE */
    private String goCBRef = "MASCTRL/LLN0$GO$GOOSE_outputs_control"; // 163 bytes
    // private String goCBRef = "MASCTRL/LLN0$GO$GOOSE"; // 147 bytes

    /* Максимальное время ожидания GOOSE-сообщения */
    private final byte[] timeAllowedToLive = {0x03, (byte) 0xE8};

    /* Набор данных, содержит ссылку набора данных DATA SET
    (взятого из блока управления GooseCB), значения
    элементов которого должны быть переданы */
    private String datSet = "MASCTRL/LLN0$GOOSE_outputs_1";

    /* Идентификатор приложения, содержит идентификатор логического
    устройства LD (взятый из блока управления GoCB),
    в котором размещается блок управления GoCB */
    private String goID = "gtnet_1";

    /* Временная метка, содержит момент времени, когда атрибут StNum был увеличен */
    private final byte[] t = {0x03, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};

    /* Номер состояния, содержит счетчик, показания которого увеличиваются
    на единицу каждый раз, когда послано GOOSE-сообщение и зафиксировано
    изменение значения внутри набора данных DATA-SET, определяемого
    с помощью параметра DatSet */
    private int stNum = 0x00;

    /* Порядковый номер, содержит счетчик, показания которого увеличиваются
    на единицу каждый раз, когда послано GOOSE-сообщение */
    private int sqNum = 100;

    /* Симуляция, указывает при значении логической единицы (TRUE),
    что значения в сообщении не должны использоваться для эксплуатационных целей */
    private boolean simulation = false;

    /* Версия конфигурации, представляет собой подсчет количества раз,
    когда конфигурация набора данных,имеющая ссылку, была изменена */
    private int confRev = 1;

    /* Требуется ввод в эксплуатацию, содержит атрибут NdsCom
    (взятый из блока управления GoCB) блока управления GoCB */
    private boolean ndsCom = false;
    private int numDatSetEntries = 6;
    private byte[] bitString = {0x03, 0x00, 0x00};
    private byte[] booLean = {0x00};
    private byte[] packet;
    private int length0, length1, length2, length3, length4, length5, length6, length7, length8, length9, length10, length11, length12, length13, length14, length15, length16, length17, length18, length19, length20, length21, length22, length23, length24, length25, length26, length27, length28, length29, length30, length31, length32, length33, length34, length35, length36, length37, length38, length39, length40, length41, length42, length43;

    /* Содание GOOSE пакета */
    public void createGoosePacket() {
        /* Вычисление длины всего пакета, посредтвом вычисления длины отедьлных элментов пакета пакета */
        length0 = 0; length1 = length0 + destination.length;
        length2 = length1 + source.length;
        length3 = length2 + type.length;
        length4 = length3 + appID.length;
        length5 = length4 + length.length;
        length6 = length5 + reserved1.length;
        length7 = length6 + reserved2.length;
        length8 = length7 + attributeForGoosePdu.length;
        length9 = length8 + attributeForGoCBRef.length;
        length10 = length9 + convertingToByte(getGoCBRef()).length;
        length11 = length10 + attributeForTimeAllowedToLive.length;
        length12 = length11 + timeAllowedToLive.length;
        length13 = length12 + attributeForDatSet.length;
        length14 = length13 + convertingToByte(getDatSet()).length;
        length15 = length14 + attributeForGoID.length;
        length16 = length15 + convertingToByte(getGoID()).length;
        length17 = length16 + attributeForT.length;
        length18 = length17 + t.length;
        length19 = length18 + attributeForStNum.length;
        length20 = length19 + convertingToByte(stNum).length;
        length21 = length20 + attributeForSqNum.length;
        length22 = length21 + convertingToByte(sqNum).length;
        length23 = length22 + attributeForSimulation.length;
        length24 = length23 + convertingToByte(isSimulation()).length;
        length25 = length24 + attributeForConfRev.length;
        length26 = length25 + convertingToByte(getConfRev()).length;
        length27 = length26 + attributeForNdsCom.length;
        length28 = length27 + convertingToByte(isNdsCom()).length;
        length29 = length28 + attributeForNumDatSetEntries.length;
        length30 = length29 + convertingToByte(getNumDatSetEntries()).length;
        length31 = length30 + attributeForAllDate.length;
        length32 = length31 + attributeForBitString.length;
        length33 = length32 + bitString.length;
        length34 = length33 + attributeForBoolean.length;
        length35 = length34 + booLean.length;

        length36 = length35 + attributeForBitString.length;
        length37 = length36 + bitString.length;
        length38 = length37 + attributeForBoolean.length;
        length39 = length38 + booLean.length;
        length40 = length39 + attributeForBitString.length;
        length41 = length40 + bitString.length;
        length42 = length41 + attributeForBoolean.length;
        length43 = length42 + booLean.length;
        // Длина всего пакета
        packet = new byte[length43];

        /* осуществляем запись в пакет (откуда, с какого элемента в исходнике,
        куда, c какого, сколько элементов) */
        System.arraycopy(destination, 0, packet, length0, destination.length);
        System.arraycopy(source, 0, packet, length1, source.length);
        System.arraycopy(type, 0, packet, length2, type.length);
        System.arraycopy(appID, 0, packet, length3, appID.length);
        System.arraycopy(length, 0, packet, length4, length.length);
        System.arraycopy(reserved1, 0, packet, length5, reserved1.length);
        System.arraycopy(reserved2, 0, packet, length6, reserved2.length);
        System.arraycopy(attributeForGoosePdu, 0, packet, length7, attributeForGoosePdu.length);
        System.arraycopy(attributeForGoCBRef, 0, packet, length8, attributeForGoCBRef.length);
        System.arraycopy(convertingToByte(goCBRef), 0, packet, length9, convertingToByte(goCBRef).length);
        System.arraycopy(attributeForTimeAllowedToLive, 0, packet, length10, attributeForTimeAllowedToLive.length); System.arraycopy(timeAllowedToLive, 0, packet, length11, timeAllowedToLive.length); System.arraycopy(attributeForDatSet, 0, packet, length12, attributeForDatSet.length); System.arraycopy(convertingToByte(datSet), 0, packet, length13, convertingToByte(datSet).length); System.arraycopy(attributeForGoID, 0, packet, length14, attributeForGoID.length); System.arraycopy(convertingToByte(goID), 0, packet, length15, convertingToByte(goID).length); System.arraycopy(attributeForT, 0, packet, length16, attributeForT.length); System.arraycopy(t, 0, packet, length17, t.length); System.arraycopy(attributeForStNum, 0, packet, length18, attributeForStNum.length); System.arraycopy(convertingToByte(stNum), 0, packet, length19, convertingToByte(stNum).length); System.arraycopy(attributeForSqNum, 0, packet, length20, attributeForSqNum.length); System.arraycopy(convertingToByte(sqNum), 0, packet, length21, convertingToByte(sqNum).length); System.arraycopy(attributeForSimulation, 0, packet, length22, attributeForSimulation.length); System.arraycopy(convertingToByte(simulation), 0, packet, length23, convertingToByte(simulation).length); System.arraycopy(attributeForConfRev, 0, packet, length24, attributeForConfRev.length); System.arraycopy(convertingToByte(confRev), 0, packet, length25, convertingToByte(confRev).length); System.arraycopy(attributeForNdsCom, 0, packet, length26, attributeForNdsCom.length); System.arraycopy(convertingToByte(ndsCom), 0, packet, length27,
        convertingToByte(ndsCom).length);System.arraycopy(attributeForNumDatSetEntries, 0, packet, length28, attributeForNumDatSetEntries.length); System.arraycopy(convertingToByte(numDatSetEntries), 0, packet, length29, convertingToByte(numDatSetEntries).length); System.arraycopy(attributeForAllDate, 0, packet, length30, attributeForAllDate.length); System.arraycopy(attributeForBitString, 0, packet, length31, attributeForBitString.length); System.arraycopy(bitString, 0, packet, length32, bitString.length); System.arraycopy(attributeForBoolean, 0, packet, length33, attributeForBoolean.length); System.arraycopy(booLean, 0, packet, length34, booLean.length); System.arraycopy(attributeForBitString, 0, packet, length35, attributeForBitString.length); System.arraycopy(bitString, 0, packet, length36, bitString.length); System.arraycopy(attributeForBoolean, 0, packet, length37, attributeForBoolean.length); System.arraycopy(booLean, 0, packet, length38, booLean.length); System.arraycopy(attributeForBitString, 0, packet, length39, attributeForBitString.length); System.arraycopy(bitString, 0, packet, length40, bitString.length); System.arraycopy(attributeForBoolean, 0, packet, length41, attributeForBoolean.length); System.arraycopy(booLean, 0, packet, length42, booLean.length); } /* Метод getBytes() кодирует строку в массив байтов */ public byte[] convertingToByte(String string) { return string.getBytes(StandardCharsets.UTF_8); } /* Метод valueOf() возвращает строковое представление аргументов */ public byte[] convertingToByte(int integer) { return String.valueOf(integer).getBytes(StandardCharsets.UTF_8); } public byte[] convertingToByte(float floatVar) { return String.valueOf(floatVar).getBytes(StandardCharsets.UTF_8); } public byte[] convertingToByte(Boolean bool) { if (bool) { return new byte[]{0x01}; } else { return new byte[]{0x00}; } } }
