<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>price history</title>
    <style>
      #main {
        display: none;
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
      }
      #okno {
        width: 300px;
        height: 50px;
        text-align: center;
        padding: 15px;
        border: 3px solid #0000cc;
        border-radius: 10px;
        color: #0000cc;
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        margin: auto;
      }
      #main:target {display: block;}
    </style>
  </head>

  <body>
    <div id="priceHistory">
       <h1>Price history<h1>
       <h2><h2>
       <table>
           <thead>
             <tr>
               <td align = left>
                   Start date
               </td>
               <td align = left>
                   Price
               </td>
             </tr>
           </thead>
            <c:forEach var="priceHistory" items="${product.priceHistory}">
              <tr>
                <td>
                  "${priceHistory.date}"
                </td>
                <td>
                 "${priceHistory.price}"
                </td>
              </tr>
            </c:forEach>
         </table>
    </div>
  </body>
</html>