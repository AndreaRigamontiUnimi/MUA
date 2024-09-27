/*

Copyright 2023 Massimo Santini

*/

package utils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An utility class to generate fake data, for testing purposes.
 *
 * <p>This class can be used both programmatically (invoking its methods from other classes), or
 * from the command line, by invoking the main method. In the latter case, the output is printed to
 * the standard output.
 */
public class Faker {

  private static final Random RND = new Random();

  private static final List<String> LOCALS =
      List.of(
          "eliana55",
          "mlettiere",
          "giovannicortese",
          "opiccinni",
          "vitoossani",
          "segnimercedes",
          "galileielladio",
          "ksorrentino",
          "ricciottimastandrea",
          "nossani",
          "enzosaffi",
          "stefanozabarella",
          "biancaserao",
          "augusto23",
          "telemaco31",
          "hferretti",
          "elianamazzanti",
          "ruggiero68",
          "fittipaldienrico",
          "ycerutti",
          "ipuccini",
          "idatoselli",
          "elena65",
          "flaviocanetta",
          "boitoenrico",
          "sorrentinoarnaldo",
          "rolando55",
          "francescaguarneri",
          "arnaldo57",
          "castiglionesylvia",
          "polanivalentina",
          "serafina08",
          "ninofilogamo",
          "gcarullo",
          "zzeffirelli",
          "dianapizzamano",
          "trevisangianna",
          "gonzagaisa",
          "luisa25",
          "achillemorgagni",
          "morroccomariana",
          "mariagiannelli",
          "angelo93",
          "dmastroianni",
          "gianpietrovalguarnera",
          "lucianaantonacci",
          "gloriacanova",
          "stefanibotticelli",
          "donna30",
          "hpedersoli",
          "roberto44",
          "elisafederico",
          "adelasiabonomo",
          "lauretta21",
          "vincenzo33",
          "piersantinugnes",
          "strangiofausto",
          "navarriapasqual",
          "marta71",
          "caterinacipolla",
          "fiorenzo61",
          "fischettimaurilio",
          "ocaruso",
          "metellagiorgio",
          "ceravolomarta",
          "onorbiato",
          "sandra13",
          "rcondoleo",
          "vincentio87",
          "atenulfvecoli",
          "gioachino63",
          "santino54",
          "toniacanova",
          "ozeffirelli",
          "serrigo",
          "kgeraci",
          "dolorespisani",
          "malapartecarla",
          "wmichelangeli",
          "allegrabottigliero",
          "riccardo42",
          "puccipina",
          "adelmo01",
          "umanacorda",
          "bricheseettore",
          "maurilioasmundo",
          "cabrinimirco",
          "ferrarisgiuliana",
          "tiziano85",
          "ffalier",
          "calvodaria",
          "cscarpa",
          "edoardo35",
          "gelsomina81",
          "adele59",
          "domenico18",
          "tferrabosco",
          "piergiorgio69",
          "ygravina",
          "sorrentinovirginia");
  private static final List<String> DOMAINS =
      List.of(
          "piacentini.it",
          "rusticucci.it",
          "varano.com",
          "scotto.it",
          "galvani.org",
          "procacci-mogherini.it",
          "sibilia.org",
          "piacentini.net",
          "satriani.net",
          "depero.it",
          "tanzini.com",
          "vergassola-trapani.it",
          "bosio.it",
          "niscoromni-mantegazza.it",
          "einaudi.com",
          "pizzo-cattaneo.it",
          "marino-tresoldi.it",
          "forza-bompiani.com",
          "bocca.it",
          "camilleri.net",
          "brunelleschi.it",
          "eco-gentili.it",
          "busoni.com",
          "forza.it",
          "sobrero.eu",
          "simeoni.org",
          "casini.org",
          "capecchi.org",
          "casalodi-bataglia.org",
          "cristoforetti.eu",
          "asprucci.com",
          "vasari-palombi.com",
          "iadanza-samele.it",
          "tasso.com",
          "olivetti.org",
          "meucci.org",
          "bonolis-ferragamo.eu",
          "camiscione-ferragamo.it",
          "ramazzotti.eu",
          "porcellato.eu",
          "bello.net",
          "fabbri.com",
          "monti.it",
          "trapanese.com",
          "grifeo.net",
          "pizzamano.it",
          "rubbia-asmundo.net",
          "montesano.net",
          "majorana.org",
          "bernardi-asprucci.org",
          "cendron-pertini.com",
          "abatantuono.com",
          "giradello.com",
          "papetti.eu",
          "persico-maspero.org",
          "opizzi.it",
          "dossetti.com",
          "ruberto.com",
          "zanzi.org",
          "combi.com",
          "nonis.it",
          "roncalli-vanvitelli.com",
          "bazzi.it",
          "sgalambro-liverotti.com",
          "mastandrea-bresciani.com",
          "bruscantini.eu",
          "nievo-perozzo.org",
          "borromini.net",
          "babati-castiglione.it",
          "sagnelli-letta.net",
          "tremonti.com",
          "farinelli.com",
          "draghi-colletti.it",
          "abatantuono.it",
          "marsili.it",
          "giacometti.org",
          "prati-maspero.it",
          "filzi.eu",
          "berlusconi-gianetti.it",
          "alfonsi-natta.com",
          "dona.com",
          "trillini.it",
          "lussu.it",
          "bonomo-saragat.com",
          "ricci.com",
          "pratesi-gravina.com",
          "pacomio.com",
          "cortese.com",
          "franscini.org",
          "verdi.org",
          "viola.com",
          "ungaretti-moretti.it",
          "turci.com",
          "draghi-strangio.net",
          "doglioni-bonino.com",
          "salvo-sgarbi.it",
          "finzi-faggiani.com",
          "ossani-porzio.it",
          "mengolo.net",
          "marinetti.net");
  private static final List<String> DISPLAYNAMES =
      List.of(
          "Amadeo Aloisio",
          "Sig.ra Marta Sollima",
          "Gianluca Zaguri",
          "Eraldo Gigli",
          "Nanni Condoleo-Civaschi",
          "Zaira Tosi",
          "Ramona Letta",
          "Nicola Terragni",
          "Dionigi Botticelli",
          "Carlo Montanariello",
          "Nicola Rastelli",
          "Dott. Silvia Tomaselli",
          "Arnulfo Opizzi-Busoni",
          "Rosaria Pavanello",
          "Tina Ferragamo",
          "Gioacchino Costanzi",
          "Griselda Pausini",
          "Sig.ra Victoria Ferrazzi",
          "Flavio Vecoli-Bajardi",
          "Natalia Mattarella",
          "Giancarlo Mazzanti-Letta",
          "Anita Brunello",
          "Enzo Badoer",
          "Valentina Querini",
          "Ezio Scandone-Carriera",
          "Dott. Veronica Scalfaro",
          "Giacomo Morosini-Gilardoni",
          "Lidia Gucci",
          "Sig. Alderano Pisacane",
          "Dott. Ninetta Cristoforetti",
          "Valentina Giolitti-Sraffa",
          "Sig. Girolamo Gentilini",
          "Dott. Ferdinando Tassoni",
          "Dario Rapisardi",
          "Greca Tagliafierro-Coardi",
          "Camillo Zabarella-Verdone",
          "Ornella Grimani-Giannotti",
          "Guarino Geraci",
          "Elmo Moretti",
          "Adelmo Cabrini",
          "Rosina Cociarelli-Rosselli",
          "Raffaello Capuana",
          "Dott. Enrico Verri",
          "Adelasia Callegari",
          "Valerio Moneta",
          "Liliana Tamburi",
          "Martino Desio",
          "Melania Calgari",
          "Dott. Camillo Borsellino",
          "Alessandra Giolitti",
          "Valeria Maffei",
          "Dott. Stefano Capuana",
          "Iolanda Martucci",
          "Riccardo Inzaghi",
          "Guido Luzi",
          "Zaira Vigorelli-Gabbana",
          "Melania Anguissola",
          "Dott. Azeglio Casale",
          "Anita Vidoni",
          "Matilda Ovadia-Cipolla",
          "Sig.ra Patrizia Pisano",
          "Girolamo Petruzzi",
          "Paolo Falcone",
          "Dott. Greco Chechi",
          "Vanessa Petruzzi",
          "Raffaella Galilei",
          "Costanzo Mazzeo",
          "Flora Vecoli",
          "Raffaella Juvara",
          "Dott. Uberto Bersani",
          "Matteo Sabatini",
          "Dott. Fiamma Trebbi",
          "Alphons Vidoni",
          "Sandra Inzaghi-Vittadello",
          "Alessia Jovinelli",
          "Sig. Piermaria Carullo",
          "Jolanda Cantimori",
          "Rosaria Puccini-Scotti",
          "Amalia Fieramosca-Salata",
          "Viridiana Zola",
          "Ruggiero Volta-Molesini",
          "Torquato Mancini",
          "Antonella Scarponi",
          "Giovanna Cibin",
          "Gianluigi Marinetti",
          "Tiziano Boezio",
          "Orlando Leopardi",
          "Jacopo Cassara",
          "Sole Vespa",
          "Monica Tuzzolino",
          "Fiorenzo Treves",
          "Sig.ra Livia Giacconi",
          "Sig. Gianmarco Mercati",
          "Alderano Berlusconi",
          "Lilla Manolesso",
          "Geronimo Micca",
          "Priscilla Baracca-Bettin",
          "Amadeo Agazzi",
          "Annalisa Ceschi",
          "Micheletto Pedroni");
  private static final List<String> SUBJECTS =
      List.of(
          "Previsione ricontestualizzata intangibile",
          "Applicazione switchabile regionale",
          "Archivio ridottà radicale",
          "Hardware esclusiva impattante",
          "Produttività versatile omogenea",
          "Hub miglìorata object-oriented",
          "Adattatore adattiva full-range",
          "Policy stand-alone statica",
          "Applicazione implementata asincrona",
          "Frame implementata real-time",
          "Strategia virtùale olistica",
          "Intranet multi-canale real-time",
          "Matrici digitalizzata uniforme",
          "Forza lavorò sicura nazionale",
          "Servizio clienti intuitiva impattante",
          "Database automatizzata scalabile",
          "Conoscenza bàse orizzontale ottima",
          "Infrastruttura proattiva bi-direzionale",
          "Sistema aperto progressìva nazionale",
          "Intelligenza artificiale decentralizzata basta sul contesto",
          "Hardware ridotta logistica",
          "Successo proattivà radicale",
          "Implementazione open-source didattica",
          "Metodologia organica secondaria",
          "Set di istruzioni òperativa tangibile",
          "Iniziativa ergonomica radicale",
          "Proiezione esclusiva locale",
          "Architettura migliòrata object-oriented",
          "Analizzatore dècentralizzata background",
          "Database intuitiva discreta",
          "Intelligenza artificiale switchabile tangibilé",
          "Firmware cross-platform composita",
          "Servizio clienti integrata coesiva",
          "Portale sincronizzatà esecutiva",
          "Funzionalità universale sensibile al contesto",
          "Codifica organica scalabile",
          "Sistema aperto programmabile object-oriented",
          "Sito web centralizzata valore aggiunto",
          "Gerarchia ricontestualizzata stabile",
          "Moratoria condivisibìle eco-centrica",
          "Metodologia adattiva nextgeneration",
          "Benchmark adattiva composita",
          "Help-desk fondamentale valore aggiunto",
          "Codifica mùlti-laterale responsiva",
          "Hub sinergica statica",
          "Analizzatore configurabile responsiva",
          "Contingenza visiònaria 24/7",
          "Policy ottimizzata neutrale",
          "Sito web sinergica metodica",
          "Set di istruzioni multi-làterale direzionale",
          "Core sicura discreta",
          "Iniziativa ergonomica bi-direzionale",
          "Moratoria piccola sistemica",
          "Parallelismo monitorata nazionale",
          "Soluzione internèt migliorata asincrona",
          "Produttività innovativa esecutiva",
          "Abilità stand-alone 24/7",
          "Intranet persistenté coesiva",
          "Utilizzazione polarizzata sensibile al contesto",
          "Architettura compatibile radicale",
          "Circuito programmabile basata sul contenuto",
          "Produttività riàllineata dedicata",
          "Progetto configurabile omogenea",
          "Utilizzazione totale dinamica",
          "Contingenza multi-laterale mission-critical",
          "Orchestrazione totale basta sul contesto",
          "Data-warehouse espànsa bi-direzionale",
          "Funzionalità estesa sistematica",
          "Soluzione internet stand-alone sensibile al contesto",
          "Metodologia migliòrata non-volatile",
          "Strategia apperò nazionale",
          "Strategia robusta nazionale",
          "Funzionalità totale valore aggiunto",
          "Frame ridotta dìscreta",
          "Attitudine multi-laterale ottima",
          "Help-desk esclusiva motivazionale",
          "Parallelismo reattiva incrementale",
          "Database compatìbile motivazionale",
          "Contingenza espansa bottom-line",
          "Software cross-platform valore aggiunto",
          "Infrastruttura òttimizzata esecutiva",
          "Approccio visionaria esecutiva",
          "Intranet ottimizzata statica",
          "Architettura sicùra discreta",
          "Monitoraggio organica reciproca",
          "Paradigma implementata tangibile",
          "Moderazioné polarizzata impattante",
          "Standardizzazione ricontestualizzata alto livello",
          "Algoritmo persistente real-time",
          "Software multi-canale nazionale",
          "Hub ricontestuàlizzata esplicita",
          "Intranet bilanciata motivazionale",
          "Flessibilità virtuale mobile",
          "Help-desk monitorata coerente",
          "Migrazione cross-platform quarta generazione",
          "Abilità implementata non-volatile",
          "Analizzatore sìnergica multi-tasking",
          "Parallelismo cross-platform dedicata",
          "Intranet ottimizzatà euristica",
          "Installazione decentralizzata bottom-line");
  private static final List<int[]> DATETIMES =
      List.of(
          new int[] {2023, 12, 4, 6, 1, 54, 521341},
          new int[] {2023, 12, 3, 7, 20, 4, 366974},
          new int[] {2023, 12, 4, 23, 5, 26, 859549},
          new int[] {2023, 12, 3, 22, 7, 16, 306224},
          new int[] {2023, 12, 4, 19, 48, 2, 543876},
          new int[] {2023, 12, 2, 2, 39, 42, 181962},
          new int[] {2023, 12, 3, 1, 30, 2, 702743},
          new int[] {2023, 12, 6, 14, 8, 22, 530031},
          new int[] {2023, 12, 6, 0, 44, 40, 437011},
          new int[] {2023, 12, 1, 6, 8, 41, 819922},
          new int[] {2023, 12, 1, 19, 38, 30, 361098},
          new int[] {2023, 12, 4, 22, 39, 8, 278995},
          new int[] {2023, 12, 2, 2, 39, 54, 627531},
          new int[] {2023, 12, 6, 11, 1, 9, 935052},
          new int[] {2023, 12, 4, 7, 11, 19, 977051},
          new int[] {2023, 12, 6, 11, 22, 34, 84464},
          new int[] {2023, 12, 4, 18, 54, 20, 997503},
          new int[] {2023, 12, 2, 16, 2, 12, 301584},
          new int[] {2023, 12, 3, 10, 9, 6, 467708},
          new int[] {2023, 12, 6, 15, 39, 7, 892167},
          new int[] {2023, 12, 5, 13, 8, 48, 604431},
          new int[] {2023, 12, 6, 16, 5, 45, 452137},
          new int[] {2023, 12, 2, 16, 12, 11, 707151},
          new int[] {2023, 12, 1, 8, 19, 2, 849186},
          new int[] {2023, 12, 3, 20, 41, 8, 356738},
          new int[] {2023, 12, 4, 9, 46, 48, 233447},
          new int[] {2023, 12, 3, 14, 36, 51, 12101},
          new int[] {2023, 12, 3, 23, 33, 50, 810084},
          new int[] {2023, 12, 4, 20, 9, 34, 234678},
          new int[] {2023, 12, 5, 2, 59, 31, 8702},
          new int[] {2023, 12, 3, 19, 23, 6, 93894},
          new int[] {2023, 12, 2, 12, 27, 16, 195807},
          new int[] {2023, 12, 5, 10, 0, 17, 413103},
          new int[] {2023, 12, 2, 14, 31, 51, 555840},
          new int[] {2023, 12, 2, 22, 20, 14, 991552},
          new int[] {2023, 12, 6, 3, 28, 24, 717976},
          new int[] {2023, 12, 6, 5, 35, 19, 101843},
          new int[] {2023, 12, 3, 18, 25, 4, 100541},
          new int[] {2023, 12, 6, 2, 54, 4, 107161},
          new int[] {2023, 12, 4, 5, 5, 34, 856532},
          new int[] {2023, 12, 2, 21, 50, 29, 705051},
          new int[] {2023, 12, 2, 22, 36, 45, 194873},
          new int[] {2023, 12, 4, 12, 9, 56, 60099},
          new int[] {2023, 12, 5, 13, 47, 56, 882935},
          new int[] {2023, 12, 2, 2, 57, 3, 202185},
          new int[] {2023, 12, 5, 21, 5, 18, 239617},
          new int[] {2023, 12, 4, 0, 21, 58, 424237},
          new int[] {2023, 12, 4, 10, 3, 34, 507298},
          new int[] {2023, 12, 4, 1, 9, 35, 340282},
          new int[] {2023, 12, 5, 0, 52, 58, 915885},
          new int[] {2023, 12, 3, 6, 50, 7, 613006},
          new int[] {2023, 12, 6, 4, 45, 26, 589974},
          new int[] {2023, 12, 2, 14, 15, 0, 460261},
          new int[] {2023, 12, 5, 1, 6, 53, 785933},
          new int[] {2023, 12, 6, 18, 50, 51, 816604},
          new int[] {2023, 12, 2, 14, 2, 56, 568151},
          new int[] {2023, 12, 6, 1, 18, 35, 258700},
          new int[] {2023, 12, 2, 20, 14, 10, 707170},
          new int[] {2023, 12, 5, 19, 19, 44, 212528},
          new int[] {2023, 12, 4, 8, 46, 20, 723262},
          new int[] {2023, 12, 1, 16, 2, 36, 918418},
          new int[] {2023, 12, 2, 15, 13, 19, 509550},
          new int[] {2023, 12, 6, 15, 42, 48, 841449},
          new int[] {2023, 12, 1, 13, 17, 23, 353792},
          new int[] {2023, 12, 3, 3, 8, 59, 661598},
          new int[] {2023, 12, 1, 18, 44, 16, 656971},
          new int[] {2023, 12, 2, 3, 30, 53, 935464},
          new int[] {2023, 12, 1, 8, 53, 38, 752941},
          new int[] {2023, 12, 2, 0, 9, 23, 933386},
          new int[] {2023, 12, 1, 8, 52, 50, 955581},
          new int[] {2023, 12, 1, 22, 21, 20, 50561},
          new int[] {2023, 12, 1, 16, 37, 58, 731526},
          new int[] {2023, 12, 4, 18, 57, 10, 420683},
          new int[] {2023, 12, 6, 20, 35, 56, 814053},
          new int[] {2023, 12, 3, 1, 10, 37, 290263},
          new int[] {2023, 12, 6, 20, 55, 23, 361500},
          new int[] {2023, 12, 1, 13, 55, 11, 452369},
          new int[] {2023, 12, 5, 15, 8, 39, 674251},
          new int[] {2023, 12, 6, 19, 24, 0, 794304},
          new int[] {2023, 12, 3, 21, 26, 59, 101245},
          new int[] {2023, 12, 4, 9, 5, 36, 115761},
          new int[] {2023, 12, 4, 10, 23, 27, 562760},
          new int[] {2023, 12, 6, 2, 34, 51, 495079},
          new int[] {2023, 12, 2, 5, 36, 43, 811048},
          new int[] {2023, 12, 4, 18, 14, 56, 357431},
          new int[] {2023, 12, 4, 14, 15, 2, 72131},
          new int[] {2023, 12, 5, 11, 47, 17, 160712},
          new int[] {2023, 12, 5, 9, 34, 15, 121024},
          new int[] {2023, 12, 4, 18, 34, 50, 863565},
          new int[] {2023, 12, 3, 2, 52, 36, 593929},
          new int[] {2023, 12, 1, 6, 23, 14, 831126},
          new int[] {2023, 12, 1, 8, 42, 45, 271758},
          new int[] {2023, 12, 5, 8, 3, 7, 373297},
          new int[] {2023, 12, 6, 18, 35, 40, 376451},
          new int[] {2023, 12, 1, 12, 8, 58, 514052},
          new int[] {2023, 12, 1, 1, 35, 28, 119951},
          new int[] {2023, 12, 4, 11, 37, 42, 925054},
          new int[] {2023, 12, 5, 13, 39, 25, 613511},
          new int[] {2023, 12, 6, 20, 17, 16, 863803},
          new int[] {2023, 12, 3, 3, 18, 1, 653114});
  private static final List<String> LOREM =
      List.of(
          "Doloremque perferendis eveniet. Minus veniam possimus quis nisi\ncommodi iste. Consequatur praesentium perferendis deserunt repellat\ncorrupti. Iure voluptate quam a eaque incidunt aliquid placeat.\nAccusantium ex itaque qui non. Modi id numquam ipsum. Accusamus odit\nexercitationem illo veritatis reprehenderit reprehenderit.",
          "Tempore quibusdam expedita. Minus assumenda laudantium nesciunt aut\nrepellendus. Dolor incidunt ipsam perspiciatis quibusdam. Officiis\nodio unde dignissimos amet unde ea. Cumque modi alias quia laudantium\nsunt dolorem ad. Dignissimos reprehenderit ducimus expedita. Id eos\nvoluptatum similique. Quod sequi rerum. Molestias culpa harum magni.\nLaboriosam cupiditate accusamus suscipit. Sapiente distinctio esse\nvero magni aut. Laudantium quos laboriosam eligendi fuga consequatur.",
          "In cupiditate adipisci tenetur perferendis maiores. Sed quos esse esse\nvelit cumque. Eligendi voluptates sit repellat. Eveniet error ab\neveniet rem. Laboriosam fuga quisquam placeat delectus corrupti sit\nunde. Officia possimus molestias blanditiis amet distinctio. Minus non\nmaiores facere nisi voluptatibus suscipit. Rem in dignissimos.\nSuscipit libero laudantium qui totam. Quae delectus laudantium\nofficiis aut accusamus dignissimos. Adipisci dolorem quia dolore\nofficia.",
          "Corporis fuga sapiente vitae. Vitae quibusdam pariatur saepe\nconsequatur qui soluta. Facilis occaecati optio dignissimos corrupti\namet. Ullam beatae vero quasi eius dolore. Vero molestias nemo\nrepellat dicta repellat quisquam. Dicta excepturi explicabo minus\ndeserunt aut delectus. Tempora reiciendis atque. Aliquid libero\nnumquam harum asperiores. Neque aperiam fuga ullam laudantium. Earum\nofficia fugiat dicta iure ullam non cupiditate. Possimus\nexercitationem culpa tempora esse tenetur ipsam repudiandae.\nConsequuntur sequi laboriosam nostrum dolor nobis. Temporibus\narchitecto numquam ut est.",
          "Saepe distinctio provident nihil hic eligendi. Iure blanditiis\npraesentium. Odio saepe nobis aliquid omnis corrupti dignissimos.\nEligendi reiciendis eaque debitis at quaerat dolorum. Illo officiis\noccaecati. Ducimus exercitationem exercitationem a pariatur\nconsequatur nostrum reiciendis. Ex dicta accusantium ullam libero eos\ndolor. Rerum nobis soluta nesciunt sequi quas placeat. Saepe nemo\nfugiat saepe. Nemo quam repellat fugiat repellat numquam.",
          "Qui ducimus reiciendis dolore. Natus quasi deleniti dolor. Expedita\nfuga provident voluptatem omnis maiores numquam. Nemo animi ipsum\niusto. Iure non unde incidunt animi. Dolore facere consequatur\ndignissimos porro ut sed. Voluptatibus inventore dicta distinctio\ndolorem explicabo amet. Officia doloribus quisquam nam eaque. Odio\noptio natus minima. Sint atque cum quibusdam sapiente incidunt nihil.\nAspernatur aliquid iste corrupti. Eligendi itaque voluptatum officiis\ndebitis dicta.",
          "Laudantium dolore saepe quibusdam molestiae. Eligendi nobis\nreprehenderit inventore consequuntur nulla. Vitae veniam ratione error\nincidunt ducimus laudantium. Atque adipisci dicta dolorem. In corporis\nducimus dignissimos. Et voluptate labore impedit quis ducimus repellat\nvel. Occaecati cumaperiam porro exercitationem ad. Porro aut amet ex\niste eligendi voluptatum. Assumenda impedit dicta rem quo nemo.\nNecessitatibus hic facere voluptates dignissimos non omnis.\nDignissimos veritatis adipisci molestias qui eos eos. Deserunt cumque\ndolore nesciunt quam culpa possimus. Veniam nisi ea quos maiores illum\ntemporibus.",
          "Debitis placeat totam facilis molestiae excepturi sapiente. Maxime\nsequi molestiae esse neque libero. Quo autem repellat odit mollitia.\nQui accusamus dolor facilis praesentium. Eum deserunt commodi quia\nnihil. Nostrum accusamus ullam possimus tempore distinctio\npraesentium. Ipsum est dolorum voluptate. Necessitatibus totam quaerat\nbeatae nobis voluptatibus laborum. In repellendus distinctio dolor\nrepellendus dolorum.",
          "Temporibus dicta laboriosam laudantium excepturi facilis. Odio minima\nearum. Deleniti omnis vel vel laudantium mollitia vel. Laborum vitae\nvoluptatem aut architecto eaque placeat. Aperiam perferendis\ndoloremque sapiente dolore neque saepe. Neque molestias odio ducimus\nautem aspernatur. Vitae corrupti veniam asperiores ullam nam. Modi\nquos magni iure autem eaque aperiam sed. Quaerat unde sunt minima\ndeserunt unde. Nam est odit ullam. Quisquam eos harum corporis\nrepellat.",
          "Laboriosam sunt fuga dolor. Modi fuga soluta a consequuntur.\nVoluptatem cumque voluptatum dolorum consectetur perferendis quia\npariatur. Officia aut eum rerum alias. Molestiae sint minus vero\nveritatis. Earum commodi molestiae labore repellat dolore. Repudiandae\nvelit consequuntur eum at magnam. Praesentium ducimus assumenda alias.",
          "Fugiat illo in quibusdam nisi voluptates. Assumenda id eaque\naspernatur recusandae saepe. Minima iusto fugiat consequuntur ullam\nnulla voluptates eum. Dolorem sequi accusamus illum commodi eligendi\nlabore. Quidem aut quisquam voluptates animi repellendus. Nam ad quasi\ndeserunt. Quas expedita quia deserunt cumque itaque provident. Commodi\nimpedit earum corporis soluta magnam. Ipsam consequuntur quidem id\nipsam aut quod. Fugit beatae tenetur neque iure excepturi iusto\ntempore.",
          "Magnam cum minima velit aliquam quisquam nemo. Nulla libero sit saepe\nmodi suscipit consequatur. Sequi praesentium laborum optio quasi\ndeserunt. Necessitatibus nobis enim ipsum. Incidunt expedita neque eum\nsunt dignissimos provident. Officiis quisquam dolor maxime eveniet vel\naperiam. Ex voluptatum amet consequatur dolorum nulla. Non similique\nsed voluptatibus atque sequi. Harum itaque laborum quidem nemo iste\npraesentium. Reprehenderit quas voluptas nobis iusto quia facilis\ncorporis. Neque in vero.",
          "Ullam nihil voluptas ipsa. Optio ea sint cumque assumenda mollitia.\nHarum alias enim velit. Aut ea similique voluptate. Cumque alias animi\noccaecati sint laborum. Quo magnam magnam quasi. Atque occaecati vero\nimpedit. Nihil illum ipsum inventore exercitationem. Impedit at\neveniet perspiciatis ducimus quos rem. Quo cumque fugit dolores est\nullam. Laborum natus officia sit pariatur consequatur.",
          "Tempora dolore amet ratione eum voluptate reiciendis. Dolore in\ncommodi vitae eos maiores est ea. Dolor voluptatem facere voluptas\noptio. Iusto est quisquam nulla reprehenderit natus eveniet. Aut dicta\nlaudantium nihil at laudantium. Cumque eos animi. Unde tempora\ntenetur. Quos explicabo neque officiis explicabo harum. Maxime quia\nrem cum. Repellat ab et expedita porro ab aut. Alias id occaecati\nanimi.",
          "Aspernatur voluptate asperiores. Aut sit nisi alias rem. Repellendus\nquidem fugit. Minus neque eius eos error quaerat aliquam. Consectetur\nmagni quisquam accusantium doloribus. Rem ut adipisci dolorum\nquibusdam. Magnam quaerat esse provident tempore voluptatibus.\nConsequuntur explicabo magni laborum accusantium. Exercitationem\ninventore voluptatum odio recusandae. Earum ut laborum. Libero\ndignissimos architecto.",
          "Labore architecto provident recusandae. Ipsa dicta impedit libero\nerror accusantium quibusdam. Facere voluptas magnam dolor sit\nvoluptatibus reiciendis. Eius dolorem dolores reprehenderit nemo\nmagnam. Tempora corporis sequi id. Eaque beatae error earum\nconsectetur. Atque rerum atque. Fuga ipsam tempora et aliquam aut\ndoloribus. Vitae pariatur quo corporis consequatur eaque molestiae\ntemporibus.",
          "Aliquam inventore occaecati laborum. Molestiae amet nam commodi\nimpedit fuga esse. Dolores nisi aspernatur magnam corrupti fugiat. Non\nofficia animi asperiores sapiente quas. Officia blanditiis\nperspiciatis praesentium enim fugit. Asperiores molestiae placeat\noccaecati quidem. Nesciunt laborum autem illo. Sit labore nulla illum\narchitecto non. Maiores aut nisi occaecati dolores saepe. Totam labore\nsoluta molestias omnis molestias laborum sed. Perferendis at iste ea.\nEos veniam officiis optio eaque unde.",
          "Ad repellendus ipsa doloribus reiciendis aliquid nostrum. In quibusdam\nsuscipit cumque. Sequi fuga nobis eius sint laudantium in. Molestiae\ndeserunt consequuntur. Repellat consequuntur minus officiis.\nAsperiores expedita illum aliquid. Ex quos itaque rerum. Ex recusandae\nadipisci dolorum. Explicabo cumque nesciunt voluptatum at harum. Ab\naliquam in rerum perferendis quibusdam fuga.",
          "Ut ad ipsa. Repellat fugiat veniam itaque veritatis doloribus veniam\nvoluptatibus. Quae asperiores illum. Aperiam cumque reiciendis nobis\neum beatae harum. Odit eligendi saepe ipsum laudantium provident\ncommodi. Nulla unde possimus suscipit in. Et eius neque ad vel. Natus\ndistinctio at dicta voluptatibus. Dicta consequatur nemo eius.\nQuibusdam laborum fugiat voluptatibus sunt modi. Error reprehenderit\nfugit nihil voluptates in nesciunt.",
          "Blanditiis unde sapiente at. Impedit nostrum officia. Vitae assumenda\nsimilique distinctio error in nesciunt recusandae. Vero veniam eveniet\nperspiciatis voluptatum. Deserunt accusantium aliquam dicta magni.\nQuis vel corrupti maxime minus reiciendis. Culpa autem consectetur\nmolestias distinctio neque.");

  private Faker() {}

  private static <T> T rnd(List<T> list) {
    return list.get(RND.nextInt(list.size()));
  }

  /**
   * Set the seed of the generator.
   *
   * @param seed the seed.
   */
  public static void setSeed(long seed) {
    RND.setSeed(seed);
  }

  /**
   * Returns a boolean given the probability of it beung true.
   *
   * @param p the probability.
   * @return the boolean.
   */
  public static boolean rndBool(double p) {
    return RND.nextDouble() < p;
  }

  /**
   * Returns an address.
   *
   * <p>The address is repesented by three string corresponding to the <em>display name</em>, the
   * <em>local</em>, and the <em>domain</em> part.
   *
   * @return a list of three non {@code null} string.
   */
  public static List<String> address() {
    if (rndBool(.5)) return List.of("", rnd(LOCALS), rnd(DOMAINS));
    else return List.of(rnd(DISPLAYNAMES), rnd(LOCALS), rnd(DOMAINS));
  }

  /**
   * Returns a list of addresses.
   *
   * <p>Return a list of at most {@code n} addresses, every address is given by a list of strings as
   * in {#link address()}.
   *
   * @param n the maximum number of addresses.
   * @return the list of addresses.
   */
  public static List<List<String>> addresses(int n) {
    int i = 1 + RND.nextInt(n);
    List<List<String>> lst = new ArrayList<>(i);
    while (i-- > 0) lst.add(address());
    return lst;
  }

  /**
   * Returns a random subhect.
   *
   * @return the subject.
   */
  public static String subject() {
    return rnd(SUBJECTS);
  }

  /**
   * Returns a random date.
   *
   * @return the date.
   */
  public static ZonedDateTime date() {
    int[] d = rnd(DATETIMES);
    return ZonedDateTime.of(d[0], d[1], d[2], d[3], d[4], d[5], d[6], DateEncoding.EUROPE_ROME);
  }

  /**
   * Returns a random text.
   *
   * @return the text.
   */
  public static String text() {
    return rnd(LOREM);
  }

  /**
   * Produces some fake data
   *
   * <p>This program produces some fake data, its arguments are:
   *
   * <ul>
   *   <li>the <em>seed</em> of the generator (optional)
   *   <li>the <em>type</em> of data to produce (mandatory), it can be: {@code A} for addresses,
   *       {@code S} for subjects, {@code D} for dates, {@code T} for text.
   *   <li>the <em>number</em> of items to generate.
   * </ul>
   *
   * @param args the arguments.
   */
  public static void main(String[] args) {
    if (args.length < 2 || args.length > 3) {
      System.err.println("Usage: Faker [seed] <A|S|D|T> <n>");
      System.exit(1);
    }
    final char what;
    final int many;
    if (args.length == 3) {
      Faker.setSeed(Long.parseLong(args[0]));
      what = args[1].charAt(0);
      many = Integer.parseInt(args[2]);
    } else {
      what = args[0].charAt(0);
      many = Integer.parseInt(args[1]);
    }
    switch (what) {
      case 'A':
        for (int i = 0; i < many; i++) {
          final String adddres = address().toString();
          System.out.println(adddres.substring(1, adddres.length() - 1));
        }
        break;
      case 'S':
        for (int i = 0; i < many; i++) System.out.println(subject());
        break;
      case 'D':
        for (int i = 0; i < many; i++) System.out.println(DateEncoding.encode(date()));
        break;
      case 'T':
        for (int i = 0; i < many; i++) System.out.println(text());
        break;
      default:
        System.err.println("Unknown option: " + what);
        break;
    }
  }
}
