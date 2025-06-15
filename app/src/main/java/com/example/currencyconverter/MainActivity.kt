package com.example.currencyconverter

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var resultTextView: TextView
    private lateinit var editTextFrom: EditText
    private lateinit var editTextTo: EditText
    private lateinit var adapterFrom: CurrencyAdapter
    private lateinit var adapterTo: CurrencyAdapter
    private lateinit var indicative : TextView
    private lateinit var currencyItems: List<CurrencyItem>
    private lateinit var btnSwap : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val currencyItems = listOf(

            CurrencyItem(R.drawable.af, "AFN"), // Afghanistan
            CurrencyItem(R.drawable.al, "ALL"), // Albania
            CurrencyItem(R.drawable.dz, "DZD"), // Algeria
            CurrencyItem(R.drawable.ad, "EUR"), // Andorra
            CurrencyItem(R.drawable.ao, "AOA"), // Angola
            CurrencyItem(R.drawable.ag, "XCD"), // Antigua and Barbuda
            CurrencyItem(R.drawable.ar, "ARS"), // Argentina
            CurrencyItem(R.drawable.am, "AMD"), // Armenia
            CurrencyItem(R.drawable.au, "AUD"), // Australia
            CurrencyItem(R.drawable.at, "EUR"), // Austria
            CurrencyItem(R.drawable.az, "AZN"), // Azerbaijan
            CurrencyItem(R.drawable.bs, "BSD"), // Bahamas
            CurrencyItem(R.drawable.bh, "BHD"), // Bahrain
            CurrencyItem(R.drawable.bd, "BDT"), // Bangladesh
            CurrencyItem(R.drawable.bb, "BBD"), // Barbados
            CurrencyItem(R.drawable.by, "BYR"), // Belarus
            CurrencyItem(R.drawable.be, "EUR"), // Belgium
            CurrencyItem(R.drawable.bz, "BZD"), // Belize
            CurrencyItem(R.drawable.bj, "XOF"), // Benin
            CurrencyItem(R.drawable.bt, "BTN"), // Bhutan
            CurrencyItem(R.drawable.bo, "BOB"), // Bolivia
            CurrencyItem(R.drawable.ba, "BAM"), // Bosnia and Herzegovina
            CurrencyItem(R.drawable.bw, "BWP"), // Botswana
            CurrencyItem(R.drawable.br, "BRL"), // Brazil
            CurrencyItem(R.drawable.bn, "BND"), // Brunei
            CurrencyItem(R.drawable.bg, "BGN"), // Bulgaria
            CurrencyItem(R.drawable.bf, "XOF"), // Burkina Faso
            CurrencyItem(R.drawable.bi, "BIF"), // Burundi
            CurrencyItem(R.drawable.kh, "KHR"), // Cambodia
            CurrencyItem(R.drawable.cm, "XAF"), // Cameroon
            CurrencyItem(R.drawable.ca, "CAD"), // Canada
            CurrencyItem(R.drawable.cv, "CVE"), // Cape Verde
            CurrencyItem(R.drawable.ky, "KYD"), // Cayman Islands
            CurrencyItem(R.drawable.cf, "XAF"), // Central African Republic
            CurrencyItem(R.drawable.td, "XAF"), // Chad
            CurrencyItem(R.drawable.cl, "CLP"), // Chile
            CurrencyItem(R.drawable.cn, "CNY"), // China
            CurrencyItem(R.drawable.co, "COP"), // Colombia
            CurrencyItem(R.drawable.km, "KMF"), // Comoros
            CurrencyItem(R.drawable.cg, "XAF"), // Congo
            CurrencyItem(R.drawable.cd, "CDF"), // Congo, Democratic Republic
            CurrencyItem(R.drawable.cr, "CRC"), // Costa Rica
            CurrencyItem(R.drawable.hr, "HRK"), // Croatia
            CurrencyItem(R.drawable.cu, "CUP"), // Cuba
            CurrencyItem(R.drawable.cy, "EUR"), // Cyprus
            CurrencyItem(R.drawable.cz, "CZK"), // Czech Republic
            CurrencyItem(R.drawable.dk, "DKK"), // Denmark
            CurrencyItem(R.drawable.dj, "DJF"), // Djibouti
            CurrencyItem(R.drawable.dm, "XCD"), // Dominica
            CurrencyItem(R.drawable.doo, "DOP"), // Dominican Republic
            CurrencyItem(R.drawable.ec, "USD"), // Ecuador
            CurrencyItem(R.drawable.eg, "EGP"), // Egypt
            CurrencyItem(R.drawable.sv, "SVC"), // El Salvador
            CurrencyItem(R.drawable.gq, "XAF"), // Equatorial Guinea
            CurrencyItem(R.drawable.er, "ERN"), // Eritrea
            CurrencyItem(R.drawable.ee, "EUR"), // Estonia
            CurrencyItem(R.drawable.et, "ETB"), // Ethiopia
            CurrencyItem(R.drawable.fj, "FJD"), // Fiji
            CurrencyItem(R.drawable.fi, "EUR"), // Finland
            CurrencyItem(R.drawable.fr, "EUR"), // France
            CurrencyItem(R.drawable.gf, "EUR"), // French Guiana
            CurrencyItem(R.drawable.pf, "XPF"), // French Polynesia
            CurrencyItem(R.drawable.ga, "XAF"), // Gabon
            CurrencyItem(R.drawable.gm, "GMD"), // Gambia
            CurrencyItem(R.drawable.ge, "GEL"), // Georgia
            CurrencyItem(R.drawable.de, "EUR"), // Germany
            CurrencyItem(R.drawable.gh, "GHS"), // Ghana
            CurrencyItem(R.drawable.gr, "EUR"), // Greece
            CurrencyItem(R.drawable.gd, "XCD"), // Grenada
            CurrencyItem(R.drawable.gp, "EUR"), // Guadeloupe
            CurrencyItem(R.drawable.gt, "GTQ"), // Guatemala
            CurrencyItem(R.drawable.gg, "GBP"), // Guernsey
            CurrencyItem(R.drawable.gn, "GNF"), // Guinea
            CurrencyItem(R.drawable.gw, "XOF"), // Guinea-Bissau
            CurrencyItem(R.drawable.gy, "GYD"), // Guyana
            CurrencyItem(R.drawable.ht, "HTG"), // Haiti
            CurrencyItem(R.drawable.va, "EUR"), // Vatican City
            CurrencyItem(R.drawable.hn, "HNL"), // Honduras
            CurrencyItem(R.drawable.hk, "HKD"), // Hong Kong
            CurrencyItem(R.drawable.hu, "HUF"), // Hungary
            CurrencyItem(R.drawable.iss, "ISK"), // Iceland
            CurrencyItem(R.drawable.ind, "INR"), // India
            CurrencyItem(R.drawable.id, "IDR"), // Indonesia
            CurrencyItem(R.drawable.ir, "IRR"), // Iran
            CurrencyItem(R.drawable.iq, "IQD"), // Iraq
            CurrencyItem(R.drawable.ie, "EUR"), // Ireland
            CurrencyItem(R.drawable.il, "ILS"), // Israel
            CurrencyItem(R.drawable.it, "EUR"), // Italy
            CurrencyItem(R.drawable.jm, "JMD"), // Jamaica
            CurrencyItem(R.drawable.jp, "JPY"), // Japan
            CurrencyItem(R.drawable.jo, "JOD"), // Jordan
            CurrencyItem(R.drawable.kz, "KZT"), // Kazakhstan
            CurrencyItem(R.drawable.ke, "KES"), // Kenya
            CurrencyItem(R.drawable.ki, "AUD"), // Kiribati
            CurrencyItem(R.drawable.kw, "KWD"), // Kuwait
            CurrencyItem(R.drawable.kg, "KGS"), // Kyrgyzstan
            CurrencyItem(R.drawable.la, "LAK"), // Laos
            CurrencyItem(R.drawable.lv, "EUR"), // Latvia
            CurrencyItem(R.drawable.lb, "LBP"), // Lebanon
            CurrencyItem(R.drawable.ls, "LSL"), // Lesotho
            CurrencyItem(R.drawable.lr, "LRD"), // Liberia
            CurrencyItem(R.drawable.ly, "LYD"), // Libya
            CurrencyItem(R.drawable.li, "CHF"), // Liechtenstein
            CurrencyItem(R.drawable.lt, "EUR"), // Lithuania
            CurrencyItem(R.drawable.lu, "EUR"), // Luxembourg
            CurrencyItem(R.drawable.mo, "MOP"), // Macao
            CurrencyItem(R.drawable.mk, "MKD"), // Macedonia
            CurrencyItem(R.drawable.mg, "MGA"), // Madagascar
            CurrencyItem(R.drawable.mw, "MWK"), // Malawi
            CurrencyItem(R.drawable.my, "MYR"), // Malaysia
            CurrencyItem(R.drawable.mv, "MVR"), // Maldives
            CurrencyItem(R.drawable.ml, "XOF"), // Mali
            CurrencyItem(R.drawable.mt, "EUR"), // Malta
            CurrencyItem(R.drawable.mh, "USD"), // Marshall Islands
            CurrencyItem(R.drawable.mq, "EUR"), // Martinique
            CurrencyItem(R.drawable.mr, "MRU"), // Mauritania
            CurrencyItem(R.drawable.mu, "MUR"), // Mauritius
            CurrencyItem(R.drawable.yt, "EUR"), // Mayotte
            CurrencyItem(R.drawable.mx, "MXN"), // Mexico
            CurrencyItem(R.drawable.fm, "XPF"), // Micronesia
            CurrencyItem(R.drawable.md, "MDL"), // Moldova
            CurrencyItem(R.drawable.mc, "EUR"), // Monaco
            CurrencyItem(R.drawable.mn, "MNT"), // Mongolia
            CurrencyItem(R.drawable.me, "EUR"), // Montenegro
            CurrencyItem(R.drawable.ms, "XCD"), // Montserrat
            CurrencyItem(R.drawable.ma, "MAD"), // Morocco
            CurrencyItem(R.drawable.mz, "MZN"), // Mozambique
            CurrencyItem(R.drawable.mm, "MMK"), // Myanmar
            CurrencyItem(R.drawable.na, "NAD"), // Namibia
            CurrencyItem(R.drawable.nr, "AUD"), // Nauru
            CurrencyItem(R.drawable.np, "NPR"), // Nepal
            CurrencyItem(R.drawable.nl, "EUR"), // Netherlands
            CurrencyItem(R.drawable.nc, "XPF"), // New Caledonia
            CurrencyItem(R.drawable.nz, "NZD"), // New Zealand
            CurrencyItem(R.drawable.ni, "NIO"), // Nicaragua
            CurrencyItem(R.drawable.ne, "XOF"), // Niger
            CurrencyItem(R.drawable.ng, "NGN"), // Nigeria
            CurrencyItem(R.drawable.nu, "AUD"), // Niue
            CurrencyItem(R.drawable.nf, "AUD"), // Norfolk Island
            CurrencyItem(R.drawable.mp, "USD"), // Northern Mariana Islands
            CurrencyItem(R.drawable.no, "NOK"), // Norway
            CurrencyItem(R.drawable.om, "OMR"), // Oman
            CurrencyItem(R.drawable.pk, "PKR"), // Pakistan
            CurrencyItem(R.drawable.pw, "USD"), // Palau
            CurrencyItem(R.drawable.ps, "ILS"), // Palestine
            CurrencyItem(R.drawable.pa, "PAB"), // Panama
            CurrencyItem(R.drawable.pg, "PGK"), // Papua New Guinea
            CurrencyItem(R.drawable.py, "PYG"), // Paraguay
            CurrencyItem(R.drawable.pe, "PEN"), // Peru
            CurrencyItem(R.drawable.ph, "PHP"), // Philippines
            CurrencyItem(R.drawable.pn, "NZD"), // Pitcairn
            CurrencyItem(R.drawable.pl, "PLN"), // Poland
            CurrencyItem(R.drawable.pt, "EUR"), // Portugal
            CurrencyItem(R.drawable.pr, "USD"), // Puerto Rico
            CurrencyItem(R.drawable.qa, "QAR"), // Qatar
            CurrencyItem(R.drawable.re, "EUR"), // Réunion
            CurrencyItem(R.drawable.ro, "RON"), // Romania
            CurrencyItem(R.drawable.ru, "RUB"), // Russia
            CurrencyItem(R.drawable.rw, "RWF"), // Rwanda
            CurrencyItem(R.drawable.bl, "EUR"), // Saint Barthélemy
            CurrencyItem(R.drawable.sh, "SHP"), // Saint Helena
            CurrencyItem(R.drawable.kn, "XCD"), // Saint Kitts and Nevis
            CurrencyItem(R.drawable.lc, "XCD"), // Saint Lucia
            CurrencyItem(R.drawable.mf, "EUR"), // Saint Martin
            CurrencyItem(R.drawable.pm, "EUR"), // Saint Pierre and Miquelon
            CurrencyItem(R.drawable.vc, "XCD"), // Saint Vincent and the Grenadines
            CurrencyItem(R.drawable.ws, "WST"), // Samoa
            CurrencyItem(R.drawable.sm, "EUR"), // San Marino
            CurrencyItem(R.drawable.st, "STD"), // São Tomé and Príncipe
            CurrencyItem(R.drawable.sa, "SAR"), // Saudi Arabia
            CurrencyItem(R.drawable.sn, "XOF"), // Senegal
            CurrencyItem(R.drawable.rs, "RSD"), // Serbia
            CurrencyItem(R.drawable.sc, "SCR"), // Seychelles
            CurrencyItem(R.drawable.sl, "SLL"), // Sierra Leone
            CurrencyItem(R.drawable.sg, "SGD"), // Singapore
            CurrencyItem(R.drawable.sk, "EUR"), // Slovakia
            CurrencyItem(R.drawable.si, "EUR"), // Slovenia
            CurrencyItem(R.drawable.sb, "SBD"), // Solomon Islands
            CurrencyItem(R.drawable.so, "SOS"), // Somalia
            CurrencyItem(R.drawable.za, "ZAR"), // South Africa
            CurrencyItem(R.drawable.ss, "SSP"), // South Sudan
            CurrencyItem(R.drawable.es, "EUR"), // Spain
            CurrencyItem(R.drawable.lk, "LKR"), // Sri Lanka
            CurrencyItem(R.drawable.sd, "SDG"), // Sudan
            CurrencyItem(R.drawable.sr, "SRD"), // Suriname
            CurrencyItem(R.drawable.se, "SEK"), // Sweden
            CurrencyItem(R.drawable.ch, "CHF"), // Switzerland
            CurrencyItem(R.drawable.sy, "SYP"), // Syria
            CurrencyItem(R.drawable.tw, "TWD"), // Taiwan
            CurrencyItem(R.drawable.tj, "TJS"), // Tajikistan
            CurrencyItem(R.drawable.tz, "TZS"), // Tanzania
            CurrencyItem(R.drawable.th, "THB"), // Thailand
            CurrencyItem(R.drawable.tl, "USD"), // Timor-Leste
            CurrencyItem(R.drawable.tg, "XOF"), // Togo
            CurrencyItem(R.drawable.to, "TOP"), // Tonga
            CurrencyItem(R.drawable.tt, "TTD"), // Trinidad and Tobago
            CurrencyItem(R.drawable.tn, "TND"), // Tunisia
            CurrencyItem(R.drawable.tr, "TRY"), // Turkey
            CurrencyItem(R.drawable.tm, "TMT"), // Turkmenistan
            CurrencyItem(R.drawable.tv, "AUD"), // Tuvalu
            CurrencyItem(R.drawable.ug, "UGX"), // Uganda
            CurrencyItem(R.drawable.ua, "UAH"), // Ukraine
            CurrencyItem(R.drawable.ae, "AED"), // United Arab Emirates
            CurrencyItem(R.drawable.gb, "GBP"), // United Kingdom
            CurrencyItem(R.drawable.us, "USD"), // United States
            CurrencyItem(R.drawable.uy, "UYU"), // Uruguay
            CurrencyItem(R.drawable.uz, "UZS"), // Uzbekistan
            CurrencyItem(R.drawable.vu, "VUV"), // Vanuatu
            CurrencyItem(R.drawable.va, "EUR"), // Vatican City
            CurrencyItem(R.drawable.ve, "VES"), // Venezuela
            CurrencyItem(R.drawable.vn, "VND"), // Vietnam
            CurrencyItem(R.drawable.ye, "YER"), // Yemen
            CurrencyItem(R.drawable.zm, "ZMW"), // Zambia
            CurrencyItem(R.drawable.zw, "ZWL")  // Zimbabwe


            // Add more currency items here
        )

        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        resultTextView = findViewById(R.id.editTextto)
        editTextFrom = findViewById(R.id.editTextFrom)
        editTextTo = findViewById(R.id.editTextto)
        indicative = findViewById(R.id.indicative)

        adapterFrom = CurrencyAdapter(this, currencyItems)
        spinnerFrom.adapter = adapterFrom

        adapterTo = CurrencyAdapter(this, currencyItems)
        spinnerTo.adapter = adapterTo

   btnSwap = findViewById(R.id.btnswap)
      btnSwap.setOnClickListener {
      swapSpinnerSelections()
      }



        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                fetchExchangeRate()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                fetchExchangeRate()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        editTextFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                fetchExchangeRate()
            }
        })
    }

    private fun fetchExchangeRate() {
        val selectedFromCurrency = (spinnerFrom.selectedItem as CurrencyItem).currencyCode
        val selectedToCurrency = (spinnerTo.selectedItem as CurrencyItem).currencyCode
        val amountText = editTextFrom.text.toString()
        val amount = if (amountText.isNotEmpty()) amountText.toDouble() else 1.0

        val apiService = RetrofitInstance.api
        apiService.getLatestRates(selectedFromCurrency).enqueue(object : Callback<ExchangeRateResponse> {
            override fun onResponse(call: Call<ExchangeRateResponse>, response: Response<ExchangeRateResponse>) {
                if (response.isSuccessful) {
                    val exchangeRateResponse = response.body()
                    exchangeRateResponse?.let {
                        val rates = it.rates
                        val exchangeRate = rates[selectedToCurrency]
                        exchangeRate?.let { rate ->
                            val convertedAmount = amount * rate
                            val result = "$amount $selectedFromCurrency = $convertedAmount $selectedToCurrency"
                            resultTextView.text = result
                            val result2 = "1 $selectedFromCurrency = $rate $selectedToCurrency"
                            indicative.text = result2

                            editTextTo.setText(convertedAmount.toString())
                        } ?: run {
                            Log.e("MainActivity", "Exchange rate not found for $selectedToCurrency")
                            resultTextView.text = "Exchange rate not found"
                            editTextTo.setText("")
                        }
                    } ?: run {
                        Log.e("MainActivity", "Response body is null")
                        resultTextView.text = "Response body is null"
                        editTextTo.setText("")
                    }
                } else {
                    Log.e("MainActivity", "Error response: ${response.errorBody()?.string()}")
                    resultTextView.text = "Error fetching rates"
                    editTextTo.setText("")
                }
            }

            override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                Log.e("MainActivity", "Error fetching rates", t)
                resultTextView.text = "Error fetching rates"
                editTextTo.setText("")
            }
        })
    }


    private fun swapSpinnerSelections() {
        val fromPosition = spinnerFrom.selectedItemPosition
        val toPosition = spinnerTo.selectedItemPosition

        spinnerFrom.setSelection(toPosition)
        spinnerTo.setSelection(fromPosition)
    }



}
