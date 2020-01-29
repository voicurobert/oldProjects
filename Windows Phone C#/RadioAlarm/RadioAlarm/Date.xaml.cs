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

namespace RadioAlarm {
    public partial class Date : PhoneApplicationPage,IDateTimePickerPage {
        public Date() {
            InitializeComponent();
        }
        #region IDateTimePickerPage Members

        public DateTime? Value {
            get;
            set;
        }

        #endregion

        private void DateButton_Click( object sender, RoutedEventArgs e ) {
            Value = DateTime.Now;
            NavigationService.GoBack();
        }
    }
}