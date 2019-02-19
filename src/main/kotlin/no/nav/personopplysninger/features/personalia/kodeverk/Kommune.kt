package no.nav.personopplysninger.features.personalia.kodeverk

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Kommune {

    private val log: Logger = LoggerFactory.getLogger(Kommune::class.java)

    private val kommunenrTilKommunenavn: Map<String, String>

    fun kommunenavn(kommunenr: String): String {
        try {
            return kommunenrTilKommunenavn.getValue(kommunenr)
        } catch (e: NoSuchElementException) {
            log.warn("Hardkodet kopi av kodeverk for kommunenenr mangler gitt kommunenr [" + kommunenr + "]", e)
            return "-"
        }
    }

    init {
        val m: MutableMap<String, String> = mutableMapOf()
        m.put("0101", "Halden")
        m.put("0104", "Moss")
        m.put("0105", "Sarpsborg")
        m.put("0106", "Fredrikstad")
        m.put("0111", "Hvaler")
        m.put("0118", "Aremark")
        m.put("0119", "Marker")
        m.put("0121", "Rømskog")
        m.put("0122", "Trøgstad")
        m.put("0123", "Spydeberg")
        m.put("0124", "Askim")
        m.put("0125", "Eidsberg")
        m.put("0127", "Skiptvet")
        m.put("0128", "Rakkestad ")
        m.put("0135", "Råde")
        m.put("0136", "Rygge")
        m.put("0137", "Våler")
        m.put("0138", "Hobøl")
        m.put("0211", "Vestby")
        m.put("0213", "Ski")
        m.put("0214", "Ås")
        m.put("0215", "Frogn")
        m.put("0216", "Nesodden")
        m.put("0217", "Oppegård")
        m.put("0219", "Bærum")
        m.put("0220", "Asker")
        m.put("0221", "Aurskog - Høland")
        m.put("0226", "Sørum")
        m.put("0227", "Fet")
        m.put("0228", "Rælinge")
        m.put("0229", "Enebakk")
        m.put("0230", "Lørenskog")
        m.put("0231", "Skedsmo")
        m.put("0233", "Nittedal")
        m.put("0234", "Gjerdrum")
        m.put("0235", "Ullensaker")
        m.put("0236", "Nes")
        m.put("0237", "Eidsvoll")
        m.put("0238", "Nannestad")
        m.put("0239", "Hurdal")
        m.put("0301", "Oslo")
        m.put("0402", "Kongsvinger")
        m.put("0403", "Hamar")
        m.put("0412", "Ringsaker")
        m.put("0415", "Løten")
        m.put("0417", "Stange")
        m.put("0418", "Nord-Odal")
        m.put("0419", "Sør-Odal")
        m.put("0420", "Eidskog")
        m.put("0423", "Grue")
        m.put("0425", "Åsnes")
        m.put("0426", "Våler")
        m.put("0427", "Elverum")
        m.put("0428", "Trysil")
        m.put("0429", "Åmot")
        m.put("0430", "Stor-Elvdal")
        m.put("0432", "Rendalen")
        m.put("0434", "Engerdal")
        m.put("0436", "Tolga")
        m.put("0437", "Tynset")
        m.put("0438", "Alvdal")
        m.put("0439", "Folldal")
        m.put("0441", "Os")
        m.put("0501", "Lillehammer")
        m.put("0502", "Gjøvik")
        m.put("0511", "Dovre")
        m.put("0512", "Lesja")
        m.put("0513", "Skjåk)")
        m.put("0514", "Lom")
        m.put("0515", "Vågå")
        m.put("0516", "Nord-Fron")
        m.put("0517", "Sel")
        m.put("0519", "Sør-Fron")
        m.put("0520", "Ringebu")
        m.put("0521", "Øyer")
        m.put("0522", "Gausdal")
        m.put("0528", "Østre Toten")
        m.put("0529", "Vestre Toten")
        m.put("0532", "Jevnaker")
        m.put("0533", "Lunner")
        m.put("0534", "Gran")
        m.put("0536", "Søndre Land")
        m.put("0538", "Nordre Land")
        m.put("0540", "Sør-Aurdal")
        m.put("0541", "Etnedal")
        m.put("0542", "Nord-Aurdal")
        m.put("0543", "Vestre Slidre")
        m.put("0544", "Øystre Slidre")
        m.put("0545", "Vang")
        m.put("0602", "Drammen")
        m.put("0604", "Kongsberg")
        m.put("0605", "Ringerike")
        m.put("0612", "Hole")
        m.put("0615", "Flå")
        m.put("0616", "Nes")
        m.put("0617", "Gol")
        m.put("0618", "Hemsedal")
        m.put("0619", "Ål")
        m.put("0620", "Hol")
        m.put("0621", "Sigdal")
        m.put("0622", "Krødsherad")
        m.put("0623", "Modum")
        m.put("0624", "Øvre Eiker")
        m.put("0625", "Nedre Eiker")
        m.put("0626", "Lier")
        m.put("0627", "Røyken")
        m.put("0628", "Hurum")
        m.put("0631", "Flesberg")
        m.put("0632", "Rollag")
        m.put("0633", "Nore og Uvdal")
        m.put("0701", "Horten")
        m.put("0704", "Tønsberg")
        m.put("0710", "Sandefjord")
        m.put("0711", "Svelvik")
        m.put("0712", "Larvik")
        m.put("0713", "Sande")
        m.put("0715", "Holmestrand")
        m.put("0716", "Re")
        m.put("0729", "Færder")
        m.put("0805", "Porsgrunn")
        m.put("0806", "Skien")
        m.put("0807", "Notodden")
        m.put("0811", "Siljan")
        m.put("0814", "Bamble")
        m.put("0815", "Kragerø")
        m.put("0817", "Drangedal")
        m.put("0819", "Nome")
        m.put("0821", "Bø")
        m.put("0822", "Sauherad")
        m.put("0826", "Tinn")
        m.put("0827", "Hjartdal")
        m.put("0828", "Seljord")
        m.put("0829", "Kviteseid")
        m.put("0830", "Nissedal")
        m.put("0831", "Fyresdal")
        m.put("0833", "Tokke")
        m.put("0834", "Vinje")
        m.put("0901", "Risør")
        m.put("0904", "Grimstad")
        m.put("0906", "Arendal")
        m.put("0911", "Gjerstad")
        m.put("0912", "Vegårshei")
        m.put("0914", "Tvedestrand")
        m.put("0919", "Froland")
        m.put("0926", "Lillesand")
        m.put("0928", "Birkenes")
        m.put("0929", "Åmli")
        m.put("0935", "Iveland")
        m.put("0937", "Evje og Hornnes")
        m.put("0938", "Bygland")
        m.put("0940", "Valle")
        m.put("0941", "Bykle")
        m.put("1001", "Kristiansand")
        m.put("1002", "Mandal")
        m.put("1003", "Farsund")
        m.put("1004", "Flekkefjord")
        m.put("1014", "Vennesla")
        m.put("1017", "Songdalen")
        m.put("1018", "Søgne")
        m.put("1021", "Marnardal")
        m.put("1026", "Åseral")
        m.put("1027", "Audnedal")
        m.put("1029", "Lindesnes")
        m.put("1032", "Lyngdal")
        m.put("1034", "Hægebostad")
        m.put("1037", "Kvinesdal")
        m.put("1046", "Sirdal")
        m.put("1101", "Eigersund")
        m.put("1102", "Sandnes")
        m.put("1103", "Stavanger")
        m.put("1106", "Haugesund")
        m.put("1111", "Sokndal")
        m.put("1112", "Lund")
        m.put("1114", "Bjerkreim")
        m.put("1119", "Hå")
        m.put("1120", "Klepp")
        m.put("1121", "Time")
        m.put("1122", "Gjesdal")
        m.put("1124", "Sola")
        m.put("1127", "Randaberg")
        m.put("1129", "Forsand")
        m.put("1130", "Strand")
        m.put("1133", "Hjelmeland")
        m.put("1134", "Suldal")
        m.put("1135", "Sauda")
        m.put("1141", "Finnøy")
        m.put("1142", "Rennesøy")
        m.put("1144", "Kvitsøy")
        m.put("1145", "Bokn")
        m.put("1146", "Tysvær")
        m.put("1149", "Karmøy")
        m.put("1151", "Utsira")
        m.put("1160", "Vindafjord")
        m.put("1201", "Bergen")
        m.put("1211", "Etne")
        m.put("1216", "Sveio")
        m.put("1219", "Bømlo")
        m.put("1221", "Stord")
        m.put("1222", "Fitjar")
        m.put("1223", "Tysnes")
        m.put("1224", "Kvinnherad")
        m.put("1227", "Jondal")
        m.put("1228", "Odda")
        m.put("1231", "Ullensvang")
        m.put("1232", "Eidfjord")
        m.put("1233", "Ulvik")
        m.put("1234", "Granvin")
        m.put("1235", "Voss")
        m.put("1238", "Kvam")
        m.put("1241", "Fusa")
        m.put("1242", "Samnanger")
        m.put("1243", "Os")
        m.put("1244", "Austevoll")
        m.put("1245", "Sund")
        m.put("1246", "Fjell")
        m.put("1247", "Askøy")
        m.put("1251", "Vaksdal")
        m.put("1252", "Modalen")
        m.put("1253", "Osterøy")
        m.put("1256", "Meland")
        m.put("1259", "Øygarden")
        m.put("1260", "Radøy")
        m.put("1263", "Lindås")
        m.put("1264", "Austrheim")
        m.put("1265", "Fedje")
        m.put("1266", "Masfjorden")
        m.put("1401", "Flora")
        m.put("1411", "Gulen")
        m.put("1412", "Solund")
        m.put("1413", "Hyllestad")
        m.put("1416", "Høyanger")
        m.put("1417", "Vik")
        m.put("1418", "Balestrand")
        m.put("1419", "Leikanger")
        m.put("1420", "Sogndal")
        m.put("1421", "Aurland")
        m.put("1422", "Lærdal")
        m.put("1424", "Årdal")
        m.put("1426", "Luster")
        m.put("1428", "Askvoll")
        m.put("1429", "Fjaler")
        m.put("1430", "Gaular")
        m.put("1431", "Jølster")
        m.put("1432", "Førde")
        m.put("1433", "Naustdal")
        m.put("1438", "Bremanger")
        m.put("1439", "Vågsøy")
        m.put("1441", "Selje")
        m.put("1443", "Eid")
        m.put("1444", "Hornindal")
        m.put("1445", "Gloppen")
        m.put("1449", "Stryn")
        m.put("1502", "Molde")
        m.put("1504", "Ålesund")
        m.put("1505", "Kristiansund")
        m.put("1511", "Vanylven")
        m.put("1514", "Sande")
        m.put("1515", "Herøy")
        m.put("1516", "Ulstein")
        m.put("1517", "Hareid")
        m.put("1519", "Volda")
        m.put("1520", "Ørsta")
        m.put("1523", "Ørskog")
        m.put("1524", "Norddal")
        m.put("1525", "Stranda")
        m.put("1526", "Stordal")
        m.put("1528", "Sykkylven")
        m.put("1529", "Skodje")
        m.put("1531", "Sula")
        m.put("1532", "Giske")
        m.put("1534", "Haram")
        m.put("1535", "Vestnes")
        m.put("1539", "Rauma")
        m.put("1543", "Nesset")
        m.put("1545", "Midsund")
        m.put("1546", "Sandøy")
        m.put("1547", "Aukra")
        m.put("1548", "Fræna")
        m.put("1551", "Eide")
        m.put("1554", "Averøy")
        m.put("1557", "Gjemnes")
        m.put("1560", "Tingvoll")
        m.put("1563", "Sunndal")
        m.put("1566", "Surnadal")
        m.put("1571", "Halsa")
        m.put("1573", "Smøla")
        m.put("1576", "Aure")
        m.put("1804", "Bodø")
        m.put("1805", "Narvik")
        m.put("1811", "Bindal")
        m.put("1812", "Sømna")
        m.put("1813", "Brønnøy")
        m.put("1815", "Vega")
        m.put("1816", "Vevelstad")
        m.put("1818", "Herøy")
        m.put("1820", "Alstahaug")
        m.put("1822", "Leirfjord")
        m.put("1824", "Vefsn")
        m.put("1825", "Grane")
        m.put("1826", "Hattfjelldal")
        m.put("1827", "Dønna")
        m.put("1828", "Nesna")
        m.put("1832", "Hemnes")
        m.put("1833", "Rana")
        m.put("1834", "Lurøy")
        m.put("1835", "Træna")
        m.put("1836", "Rødøy")
        m.put("1837", "Meløy")
        m.put("1838", "Gildeskål")
        m.put("1839", "Beiarn")
        m.put("1840", "Saltdal")
        m.put("1841", "Fauske – Fuossko")
        m.put("1845", "Sørfold")
        m.put("1848", "Steigen")
        m.put("1849", "Hamarøy")
        m.put("1850", "Divtasvuodna – Tysfjord")
        m.put("1851", "Lødingen")
        m.put("1852", "Tjeldsund")
        m.put("1853", "Evenes")
        m.put("1854", "Ballangen")
        m.put("1856", "Røst")
        m.put("1857", "Værøy")
        m.put("1859", "Flakstad")
        m.put("1860", "Vestvågøy")
        m.put("1865", "Vågan")
        m.put("1866", "Hadsel")
        m.put("1867", "Bø")
        m.put("1868", "Øksnes")
        m.put("1870", "Sortland")
        m.put("1871", "Andøy")
        m.put("1874", "Moskenes")
        m.put("1902", "Tromsø")
        m.put("1903", "Harstad")
        m.put("1911", "Kvæfjord")
        m.put("1913", "Skånland")
        m.put("1917", "Ibestad")
        m.put("1919", "Gratangen")
        m.put("1920", "Loabák – Lavangen")
        m.put("1922", "Bardu")
        m.put("1923", "Salangen")
        m.put("1924", "Målselv")
        m.put("1925", "Sørreisa")
        m.put("1926", "Dyrøy")
        m.put("1927", "Tranøy")
        m.put("1928", "Torsken")
        m.put("1929", "Berg")
        m.put("1931", "Lenvik")
        m.put("1933", "Balsfjord")
        m.put("1936", "Karlsøy")
        m.put("1938", "Lyngen")
        m.put("1939", "Storfjord – Omasvuotna – Omasvuono")
        m.put("1940", "Gáivuotna – Kåfjord – Kaivuono")
        m.put("1941", "Skjervøy")
        m.put("1942", "Nordreisa-Ráisa-Raisi")
        m.put("1943", "Kvænangen")
        m.put("2002", "Vardø")
        m.put("2003", "Vadsø")
        m.put("2004", "Hammerfest")
        m.put("2011", "Guovdageaidnu – Kautokeino")
        m.put("2012", "Alta")
        m.put("2014", "Loppa")
        m.put("2015", "Hasvik")
        m.put("2017", "Kvalsund")
        m.put("2018", "Måsøy")
        m.put("2019", "Nordkapp")
        m.put("2020", "Porsanger – Porsá?gu – Porsanki")
        m.put("2021", "Kárášjohka – Karasjok")
        m.put("2022", "Lebesby")
        m.put("2023", "Gamvik")
        m.put("2024", "Berlevåg")
        m.put("2025", "Deatnu-Tana")
        m.put("2027", "Unjárga – Nesseby")
        m.put("2028", "Båtsfjord")
        m.put("2030", "Sør-Varanger")
        m.put("2100", "Svalbard")
        m.put("2112", "Ny-Ålesund")
        m.put("2113", "Barentsburg")
        m.put("2115", "Sveagruva")
        m.put("5001", "Trondheim")
        m.put("5004", "Steinkjer")
        m.put("5005", "Namsos")
        m.put("5011", "Hemne")
        m.put("5012", "Snillfjord")
        m.put("5013", "Hitra")
        m.put("5014", "Frøya")
        m.put("5015", "Ørland")
        m.put("5016", "Agdenes")
        m.put("5017", "Bjugn")
        m.put("5018", "Åfjord")
        m.put("5019", "Roan")
        m.put("5020", "Osen")
        m.put("5021", "Oppdal")
        m.put("5022", "Rennebu")
        m.put("5023", "Meldal")
        m.put("5024", "Orkdal")
        m.put("5025", "Røros")
        m.put("5026", "Holtålen")
        m.put("5027", "Midtre Gauldal ")
        m.put("5028", "Melhus")
        m.put("5029", "Skaun")
        m.put("5030", "Klæbu")
        m.put("5031", "Malvik")
        m.put("5032", "Selbu")
        m.put("5033", "Tydal")
        m.put("5034", "Meråker")
        m.put("5035", "Stjørdal")
        m.put("5036", "Frosta")
        m.put("5037", "Levanger")
        m.put("5038", "Verdal")
        m.put("5039", "Verran")
        m.put("5040", "Namdalseid")
        m.put("5041", "Snåase - Snåsa")
        m.put("5042", "Lierne")
        m.put("5043", "Raarvikhe – Røyrvik")
        m.put("5044", "Namsskogan")
        m.put("5045", "Grong")
        m.put("5046", "Høylandet")
        m.put("5047", "Overhalla")
        m.put("5048", "Fosnes")
        m.put("5049", "Flatanger")
        m.put("5050", "Vikna")
        m.put("5051", "Nærøy")
        m.put("5052", "Leka")
        m.put("5053", "Inderøy")
        m.put("5054", "Indre Fosen")
        m.put("5061", "Rindal")
        kommunenrTilKommunenavn = m
    }

}