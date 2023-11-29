let hours = 0;
let minutes = 0;
let seconds = 0;
let tenMillis = 0;
const appendTens = document.getElementById("tenMillis");
const appendSeconds = document.getElementById("seconds");
const appendMinutes = document.getElementById("minutes");
const appendHours = document.getElementById("hours");

const buttonStart = document.getElementById("bt__start");
const buttonStop = document.getElementById("bt__stop");
const buttonReset = document.getElementById("bt__reset");
let intervalId;
 
buttonStart.onclick = function(){
  clearInterval(intervalId)
  intervalId = setInterval(operateTimer, 10)
}
 
buttonStop.onclick = function(){
  clearInterval(intervalId)
}
 
buttonReset.onclick = function(){
  clearInterval(intervalId)
  tenMillis = 0; seconds = 0; minutes = 0; hours = 0;
  appendTens.textContent = "00"
  appendSeconds.textContent = "00"
  appendMinutes.textContent = "00"
  appendHours.textContent = "00"
}

var restMessage = document.querySelector('h4'); // <h4> 엘리먼트 선택 
restMessage.style.display = 'none' // 휴식 메세지 숨기기

function operateTimer(){
  tenMillis++;
  appendTens.textContent = tenMillis > 9 ? tenMillis : '0' + tenMillis
  if(tenMillis > 99){
    seconds++;
    appendSeconds.textContent = seconds > 9 ? seconds : '0' + seconds
    tenMillis = 0
    appendTens.textContent = "00"
  }
  
  if(seconds > 59){
    minutes++;
    appendMinutes.textContent = minutes > 9 ? minutes : '0' + minutes
    seconds = 0
    appendSeconds.textContent = "00"    
  }

  if (minutes === 50) { restMessage.style.display = 'block'; } // 휴식 메세지 나타내기

  if (minutes > 59) {
    hours++;
    restMessage.style.display = 'none';
    appendHours.textContent = hours > 9 ? hours : '0' + hours
    minutes = 0;
    appendMinutes.textContent = "00";
  }

}