using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using Microsoft.Phone.Controls;
using Microsoft.Phone.Scheduler;
using System.Windows.Threading;

using Microsoft.Devices.Radio;

namespace RadioAlarm {
    public partial class MainPage : PhoneApplicationPage {
        // Constructor
        FMRadio radio = FMRadio.Instance;
        DateTime date = new DateTime();
        DateTime time = new DateTime();
        DateTime alarmTime;
        Alarm alarm = new Alarm("Radio alarm");     
        public MainPage() {
            InitializeComponent();
            this.timePicker.ValueChanged += new EventHandler<DateTimeValueChangedEventArgs>(timePicker_ValueChanged);
            this.datePicker.ValueChanged += new EventHandler<DateTimeValueChangedEventArgs>(datePicker_ValueChanged);                
        }
        private void timePicker_ValueChanged( object sender, DateTimeValueChangedEventArgs e ) {
            time = (DateTime)e.NewDateTime;
            this.textBlock1.Text = time.ToShortTimeString();
        }
        private void datePicker_ValueChanged( object sender, DateTimeValueChangedEventArgs e ) {
            try {
                date = (DateTime)e.NewDateTime;
                this.textBlock2.Text = date.ToShortDateString();
            } catch (Exception ex) {
                Console.WriteLine(ex.Message);
            }
        }
        private void AddAlarmButton_Click( object sender, RoutedEventArgs e ) {
            try {
                alarmTime = date.Date + time.TimeOfDay;
                alarm.BeginTime = date.Date + time.TimeOfDay;
                ScheduledActionService.Add(alarm);
                MessageBox.Show("Alarm Created");
                while ((date.Date + time.TimeOfDay) >= DateTime.Now) {
                    radio.PowerMode = RadioPowerMode.Off;
                }
                    radioAlarm();
            } catch (Exception ex) {
                MessageBox.Show(ex.Message);
            }
        }
        private void radioAlarm() {
                radio.PowerMode = RadioPowerMode.On;
                radio.CurrentRegion = RadioRegion.Europe;
                radio.Frequency = 100.9;
        } 
    }
}