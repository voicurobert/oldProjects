﻿using System;
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

namespace HelloWorld {
    public partial class MyFirstXamlPage : PhoneApplicationPage {
        public MyFirstXamlPage() {
            InitializeComponent();
        }

        private void ClickMeButton_Click( object sender, RoutedEventArgs e ) {
            MyHelloWorldTextBlock.Text = "Hello Wordl!";
            

        }
    }
}