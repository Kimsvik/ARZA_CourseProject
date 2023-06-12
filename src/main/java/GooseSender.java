import lombok.Getter;
import lombok.Setter;
import org.pcap4j.core.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
/* GooseCB Блок управления общими объекто-ориетнированными событиями на ПС */
public class GooseSender {
    private GoosePacket goosePacket; // Сообщение GOOSE
    private byte[] destination;
    private byte[] source; private byte[] type;

    /* Имя, принадлежащее экземпляру GoCB */
    private Object goCBName = new Object();

    /* Имя пути, принадлежащее экземпляру GoCB */
    private String goCBRef;

    /* Максимальное время ожидания GOOSE-сообщения */
    private String timeAllowedToLive;

    /* Разрешение GOOSE, (TRUE) - разрешена посылка GOOSE-сообщений */
    private boolean goEna;

    /* Идентификация приложения, атрибут, который позволяет пользователю
    назначить уникальную системную идентификацию для приложения,
    которое выдает сообщения GOOSE, DEFAULT GoCBRef */
    private String appID; private String appRecID;

    /* Ссылка набора данных DATA-SET, значения элементов которого должны быть переданы */
    private Object dataSet = new Object();

    /* Версия конфигурации, представляет подсчет количества раз, когда конфигурация набора данных,
    имеющая ссылку, была изменена*/
    private int confRev;
    /* Требуется ввод в эксплуатацию, равно логической единице (TRUE),
    если атрибут DatSet имеет значение, равное NULL */
    private boolean ndsCom;
    private String SqNum;
    private int sendMsgCount = 0; // счетчик отправленных сообщений
    private int receiveMsgCount = 0; // счетчик полученных сообщений
    private int ignoreMsgCount = 0; // счетчик проигнорированных сообщений
    private long sendTime; // время отправки
    private long receiveTime; // время получения
    private float waitingTime; // время ожидания
    private int period; // период отправки
    private ArrayList<Integer> periods;
    private boolean condition1; // Необходимы для подтверждения что это дейчтвительно ГУС сообщение

    // Проверка по destination, source, type, а также по параметру самого GOOSE пакета – goCBRef
    private boolean condition2;
    private boolean condition3;
    private boolean condition4;
    ArrayList<Float> waitingTimes = new ArrayList<>();
    ArrayList<Long> sendTimes = new ArrayList<>();
    private List<PcapNetworkInterface> devices = null;
    private PcapHandle handle = null;

    // Функция для установки рабочего интерфейса
    public void setIface() {
        /*Получаем список устройств в системе*/
        try {
            devices = Pcaps.findAllDevs();
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }

        PcapNetworkInterface activeInterface = null; // создаем переменную для записи интерфейса

        for (PcapNetworkInterface pcapIface : devices) { // проходимся по каждому из утройств
            if (pcapIface.getDescription().equals(appID)) { // сравниваем с установоеным в Main appID
                activeInterface = pcapIface; // если совпадает то устанавливаем интрефейс
                break;
            }
        }

        if (activeInterface != null) { // если не нашли интерфейсов
            try {
                handle = activeInterface.openLive(65536, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, 50);
                // snaplen- Длина моментального снимка, то есть количество байтов, захваченных для каждого пакета
                // mode- Режим PcapNetworkInterface.PromiscuousMode
                /* timeoutMillis- Тайм-аут чтения. Большинство операционных систем буферизируют пакеты.
                ОС передают пакеты Pcap4j после заполнения буфера или истечения времени ожидания чтения.
                Должен быть неотрицательным. Может игнорироваться некоторыми ОС.*/
            } catch (PcapNativeException e) {
                e.printStackTrace();
            }
        }
    }

    /* Отправка GOOSE*/
    public void sendGOOSEMessage() {
        if (goEna) { // если отправка разрешена
            // System.out.println(1);
            try {
                handle.sendPacket(goosePacket.getPacket()); // отправляем пакет
                goosePacket.setSqNum(goosePacket.getSqNum() + 1); // увеличивем счетчик SqNum
                sendMsgCount++;
                System.out.println("Отправлено " + sendMsgCount + " GOOSE-сообщение");
                sendTime = System.nanoTime(); // получаем время отправки системы в наносекундах
                sendTimes.add(sendTime);
                //System.out.println(goosePacket.getSqNum());
                System.out.println("send time = " + sendTime);
            } catch (NotOpenException | PcapNativeException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Отправка GOOSE-сообщений заблокирована");
        }
    }
}
