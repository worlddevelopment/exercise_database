//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:49:14 AM
//

package DIaLOGIKa.b2xtranslator.WordprocessingMLMapping;

import CS2JNet.System.Xml.XmlAttribute;
import CS2JNet.System.Xml.XmlElement;
import CS2JNet.System.Xml.XmlWriter;
import DIaLOGIKa.b2xtranslator.CommonTranslatorLib.IMapping;
import DIaLOGIKa.b2xtranslator.DocFileFormat.LanguageId;
import DIaLOGIKa.b2xtranslator.DocFileFormat.LanguageId.LanguageCode;
import DIaLOGIKa.b2xtranslator.OpenXmlLib.OpenXmlNamespaces;
import DIaLOGIKa.b2xtranslator.WordprocessingMLMapping.PropertiesMapping;
import java.util.Locale;

public class LanguageIdMapping  extends PropertiesMapping implements IMapping<LanguageId>
{
    public enum LanguageType
    {
        Default,
        EastAsian,
        Complex
    }
    private XmlElement _parent;
    private LanguageType _type = LanguageType.Default;
    public LanguageIdMapping(XmlWriter writer, LanguageType type) throws Exception {
        super(writer);
        _type = type;
    }

    public LanguageIdMapping(XmlElement parentElement, LanguageType type) throws Exception {
        super(null);
        _nodeFactory = parentElement.getOwnerDocument();
        _parent = parentElement;
        _type = type;
    }

    public void apply(LanguageId lid) throws Exception {
        if (lid.Code != LanguageCode.Nothing)
        {
            String langcode = "en-US";
            try
            {
                Locale ci = new Locale(((Enum)lid.Code).ordinal());
                langcode = ci.toString();
            }
            catch (Exception __dummyCatchVar0)
            {
            }

            //langcode = getLanguageCode(lid);
            XmlAttribute att;
            switch(_type)
            {
                case Default: 
                    att = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
                    break;
                case EastAsian: 
                    att = _nodeFactory.createAttribute("w","eastAsia",OpenXmlNamespaces.WordprocessingML);
                    break;
                case Complex: 
                    att = _nodeFactory.createAttribute("w","bidi",OpenXmlNamespaces.WordprocessingML);
                    break;
                default: 
                    att = _nodeFactory.createAttribute("w","val",OpenXmlNamespaces.WordprocessingML);
                    break;
            
            }
            att.setValue(langcode);
            if (_writer != null)
            {
                att.WriteTo(_writer);
            }
            else if (_parent != null)
            {
                _parent.getAttributes().add(att);
            }
              
        }
         
    }

    private String getLanguageCode(LanguageId lid) throws Exception {
        switch(lid.Code)
        {
            case Afrikaans: 
                return "af-ZA";
            case Albanian: 
                return "sq-AL";
            case Amharic: 
                return "am-ET";
            case ArabicAlgeria: 
                return "ar-DZ";
            case ArabicBahrain: 
                return "ar-BH";
            case ArabicEgypt: 
                return "ar-EG";
            case ArabicIraq: 
                return "ar-IQ";
            case ArabicJordan: 
                return "ar-JO";
            case ArabicKuwait: 
                return "ar-KW";
            case ArabicLebanon: 
                return "ar-LB";
            case ArabicLibya: 
                return "ar-LY";
            case ArabicMorocco: 
                return "ar-MA";
            case ArabicOman: 
                return "ar-OM";
            case ArabicQatar: 
                return "ar-QA";
            case ArabicSaudiArabia: 
                return "ar-SA";
            case ArabicSyria: 
                return "ar-SY";
            case ArabicTunisia: 
                return "ar-TN";
            case ArabicUAE: 
                return "ar-AE";
            case ArabicYemen: 
                return "ar-YE";
            case Armenian: 
                return "hy-AM";
            case Assamese: 
                return "as-IN";
            case AzeriCyrillic: 
                return "az-AZ-cyrl";
            case AzeriLatin: 
                return "az-AZ-latn";
            case Basque: 
                return "eu-ES";
            case Belarusian: 
                return "be-BY";
            case Bengali: 
                return "bn-IN";
            case BengaliBangladesh: 
                return "bn-BD";
            case Bulgarian: 
                return "bg-BG";
            case Burmese: 
                return "my-MM";
            case Catalan: 
                return "ca-ES";
            case Cherokee: 
            case ChineseHongKong: 
                return "zh-HK";
            case ChineseMacao: 
                return "zh-MO";
            case ChinesePRC: 
                return "zh-CN";
            case ChineseSingapore: 
                return "zh-SG";
            case ChineseTaiwan: 
                return "zh-TW";
            case Croatian: 
                return "hr-HR";
            case Czech: 
                return "cs-CZ";
            case Danish: 
                return "da-DK";
            case Divehi: 
                return "dv-MV";
            case DutchBelgium: 
                return "nl-BE";
            case DutchNetherlands: 
                return "nl-NL";
            case Edo: 
            case EnglishAustralia: 
                return "en-AU";
            case EnglishBelize: 
                return "en-BZ";
            case EnglishCanada: 
                return "en-CA";
            case EnglishCaribbean: 
                return "en-DO";
            case EnglishHongKong: 
                return "en-HK";
            case EnglishIndia: 
                return "en-IN";
            case EnglishIndonesia: 
                return "en-ID";
            case EnglishIreland: 
                return "en-IE";
            case EnglishJamaica: 
                return "en-JM";
            case EnglishMalaysia: 
                return "en-MY";
            case EnglishNewZealand: 
                return "en-NZ";
            case EnglishPhilippines: 
                return "en-PH";
            case EnglishSingapore: 
                return "en-SG";
            case EnglishSouthAfrica: 
                return "en-ZA";
            case EnglishTrinidadAndTobago: 
                return "en-TT";
            case EnglishUK: 
                return "en-UK";
            case EnglishUS: 
                return "en-US";
            case EnglishZimbabwe: 
                return "en-ZW";
            case Estonian: 
                return "et-EE";
            case Faeroese: 
                return "fo-FO";
            case Farsi: 
            case Filipino: 
            case Finnish: 
                return "fi-FI";
            case FrenchBelgium: 
                return "fr-BE";
            case FrenchCameroon: 
                return "fr-CM";
            case FrenchCanada: 
                return "fr-CA";
            case FrenchCongoDRC: 
                return "fr-CD";
            case FrenchCotedIvoire: 
                return "fr-CI";
            case FrenchFrance: 
                return "fr-FR";
            case FrenchHaiti: 
                return "fr-HT";
            case FrenchLuxembourg: 
                return "fr-LU";
            case FrenchMali: 
                return "fr-ML";
            case FrenchMonaco: 
                return "fr-MC";
            case FrenchMorocco: 
                return "fr-MA";
            case FrenchReunion: 
                return "fr-RE";
            case FrenchSenegal: 
                return "fr-SN";
            case FrenchSwitzerland: 
                return "fr-CH";
            case FrenchWestIndies: 
                return "fr-DO";
            case FrisianNetherlands: 
                return "fy-NL";
            case Fulfulde: 
            case FYROMacedonian: 
                return "mk-MK";
            case GaelicIreland: 
                return "ga-IE";
            case GaelicScotland: 
                return "gd-UK";
            case Galician: 
                return "gl-ES";
            case Georgian: 
                return "ka-GE";
            case GermanAustria: 
                return "de-AT";
            case GermanGermany: 
                return "de-DE";
            case GermanLiechtenstein: 
                return "de-LI";
            case GermanLuxembourg: 
                return "de-LU";
            case GermanSwitzerland: 
                return "de-CH";
            case Greek: 
                return "el-GR";
            case Guarani: 
                return "gn-BR";
            case Gujarati: 
                return "gu-IN";
            case Hausa: 
                return "ha-NG";
            case Hawaiian: 
            case Hebrew: 
                return "he-IL";
            case Hindi: 
                return "hi-IN";
            case Hungarian: 
                return "hu-HU";
            case Ibibio: 
            case Icelandic: 
                return "is-IS";
            case Igbo: 
            case Indonesian: 
                return "id-ID";
            case Inuktitut: 
                return "iu-CA";
            case ItalianItaly: 
                return "it-IT";
            case ItalianSwitzerland: 
                return "it-CH";
            case Japanese: 
                return "ja-JP";
            case Kannada: 
                return "kn-ID";
            case Kanuri: 
            case Kashmiri: 
                return "ks-ID";
            case KashmiriArabic: 
                return "ks-PK";
            case Kazakh: 
                return "kk-KZ";
            case Khmer: 
            case Konkani: 
            case Korean: 
                return "ko-KR";
            case Kyrgyz: 
                return "ky-KG";
            case Lao: 
                return "lo-LA";
            case Latin: 
                return "la";
            case Latvian: 
                return "lv-LV";
            case Lithuanian: 
                return "lt-LT";
            case Malay: 
                return "ms-MY";
            case MalayBruneiDarussalam: 
                return "ms-BN";
            case Malayalam: 
                return "ml-ID";
            case Maltese: 
                return "mt-MT";
            case Manipuri: 
            case Maori: 
                return "mi-NZ";
            case Marathi: 
                return "mr-ID";
            case Mongolian: 
                return "mn-MN";
            case MongolianMongolian: 
                return "mn-MN";
            case Nepali: 
                return "ne-NP";
            case NepaliIndia: 
                return "ne-ID";
            case NorwegianBokmal: 
                return "nb-NO";
            case NorwegianNynorsk: 
                return "nn-NO";
            case Oriya: 
                return "or-ID";
            case Oromo: 
            case Papiamentu: 
            case Pashto: 
                return "ps-PK";
            case Polish: 
                return "pl-PL";
            case PortugueseBrazil: 
                return "pt-BR";
            case PortuguesePortugal: 
                return "pt-PT";
            case Punjabi: 
                return "pa-ID";
            case PunjabiPakistan: 
                return "pa-PK";
            case QuechuaBolivia: 
                return "qu-BO";
            case QuechuaEcuador: 
                return "qu-EC";
            case QuechuaPeru: 
                return "qu-PE";
            case RhaetoRomanic: 
                return "rm-CH";
            case RomanianMoldova: 
                return "ro-MD";
            case RomanianRomania: 
                return "ro-RO";
            case RussianMoldova: 
                return "ru-MD";
            case RussianRussia: 
                return "ru-RU";
            case SamiLappish: 
                return "se-FI";
            case Sanskrit: 
                return "sa-ID";
            case Sepedi: 
            case SerbianCyrillic: 
                return "sr-YU-cyrl";
            case SerbianLatin: 
                return "sr-YU-latn";
            case SindhiArabic: 
                return "sd-PK";
            case SindhiDevanagari: 
                return "sd-ID";
            case Sinhalese: 
                return "si-ID";
            case Slovak: 
                return "sk-SK";
            case Slovenian: 
                return "sl-SI";
            case Somali: 
                return "so-SO";
            case Sorbian: 
            case SpanishArgentina: 
                return "es-AR";
            case SpanishBolivia: 
                return "es-BO";
            case SpanishChile: 
                return "es-CL";
            case SpanishColombia: 
                return "es-CO";
            case SpanishCostaRica: 
                return "es-CR";
            case SpanishDominicanRepublic: 
                return "es-DO";
            case SpanishEcuador: 
                return "es-EC";
            case SpanishElSalvador: 
                return "es-SV";
            case SpanishGuatemala: 
                return "es-GT";
            case SpanishHonduras: 
                return "es-HN";
            case SpanishMexico: 
                return "es-MX";
            case SpanishNicaragua: 
                return "es-NI";
            case SpanishPanama: 
                return "es-PA";
            case SpanishParaguay: 
                return "es-PY";
            case SpanishPeru: 
                return "es-PE";
            case SpanishPuertoRico: 
                return "es-PR";
            case SpanishSpainModernSort: 
                return "es-ES";
            case SpanishSpainTraditionalSort: 
                return "es-ES";
            case SpanishUruguay: 
                return "es-UY";
            case SpanishVenezuela: 
                return "es-VE";
            case Sutu: 
            case Swahili: 
                return "sw-TZ";
            case SwedishFinland: 
                return "sv-FI";
            case SwedishSweden: 
                return "sv-SE";
            case Syriac: 
            case Tajik: 
                return "tg-TJ";
            case Tamazight: 
            case TamazightLatin: 
            case Tamil: 
                return "ta-ID";
            case Tatar: 
                return "tt-RU";
            case Telugu: 
                return "te-ID";
            default: 
                return "en-US";
        
        }
    }

}


//there is no iso code fpr cherokee
//there is no iso 639-1 code for edo
//the caribbean sea has many english speaking countires.
//we use the Dominican Republic
//there is no iso 639-1 code for farsi
//there is no iso 639-1 code for filipino
//the western caribbean sea has many french speaking countires.
//we use the Dominican Republic
//there is no iso 639-1 code for fulfulde
//there is no iso 639-1 language code for hawaiian
//there is no iso 639-1 language code for ibibio
//there is no iso 639-1 language code for ibibio
//there is no iso 639-1 language code for kanuri
//there is no iso 639-1 language code for khmer
//there is no iso 639-1 language code for konkani
//there is no iso 639-1 language code for manipuri
//also possible: no-NO
//also possible: no-NO
//there is no iso 639-1 language code for oromo
//there is no iso 639-1 language code for papiamentu
//there is no iso 639-1 language code for sepedi
//there is no iso 639-1 language code for sorbian
//there is no iso 639-1 language code for sutu
//Swahili is spoken in many east african countries, so we use tansania
//there is no iso 639-1 language code for syriac
//there is no iso 639-1 language code for tamazight
//there is no iso 639-1 language code for tamazight
//case LanguageId.LanguageCode.Thai:
//case LanguageId.LanguageCode.TibetanBhutan:
//case LanguageId.LanguageCode.TibetanPRC:
//case LanguageId.LanguageCode.TigrignaEritrea:
//case LanguageId.LanguageCode.TigrignaEthiopia:
//case LanguageId.LanguageCode.Tsonga:
//case LanguageId.LanguageCode.Tswana:
//case LanguageId.LanguageCode.Turkish:
//case LanguageId.LanguageCode.Turkmen:
//case LanguageId.LanguageCode.Ukrainian:
//case LanguageId.LanguageCode.Urdu:
//case LanguageId.LanguageCode.UzbekCyrillic:
//case LanguageId.LanguageCode.UzbekLatin:
//case LanguageId.LanguageCode.Venda:
//case LanguageId.LanguageCode.Vietnamese:
//case LanguageId.LanguageCode.Welsh:
//case LanguageId.LanguageCode.Xhosa:
//case LanguageId.LanguageCode.Yi:
//case LanguageId.LanguageCode.Yiddish:
//case LanguageId.LanguageCode.Yoruba:
//case LanguageId.LanguageCode.Zulu: