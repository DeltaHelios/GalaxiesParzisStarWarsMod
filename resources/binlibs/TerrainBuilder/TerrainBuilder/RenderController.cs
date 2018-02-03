﻿using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Windows.Forms;
using PFX;

namespace TerrainBuilder
{
    public partial class RenderController : Form
    {
        private readonly WindowVisualize _parent;
        private readonly Random _random = new Random();

        public Dictionary<int, Color> Colors = new Dictionary<int, Color>();

        public RenderController(WindowVisualize parent)
        {
            _parent = parent;
            InitializeComponent();
            
            nudSeed.Minimum = 0;
            nudSeed.Maximum = int.MaxValue;
            nudSeed.Value = _random.Next();

            Text = EmbeddedFiles.AppName;
            Icon = EmbeddedFiles.logo;

            pbTerrainColor.BackColor = _parent.TintColor;

            for (var i = 0; i < 256; i++)
                Colors.Add(i, Color.FromArgb(i, i, i));

        }

        private void bRandomize_Click(object sender, EventArgs e)
        {
            nudSeed.Value = _random.Next();
        }

        private void TerrainLayerList_FormClosing(object sender, FormClosingEventArgs e)
        {
            _parent.Kill();
        }

        private void nudSideLength_ValueChanged(object sender, EventArgs e)
        {
            _parent.SideLength = (int) nudSideLength.Value;
            _parent.ReRender();
        }

        private void nudSeed_ValueChanged(object sender, EventArgs e)
        {
            if (_parent.IsRendering())
            {
                Lumberjack.Warn("Seed change request denied, render thread busy");
                return;
            }

            _parent.ScriptedTerrainGenerator.SetSeed((long)nudSeed.Value);
            //ReRenderNoiseImage();
            _parent.ReRender();
        }

        public void ReRenderNoiseImage()
        {
            //if (Colors.Count == 0)
            //    return;
            
            //var bmp = new Bitmap(pbNoise.Width, pbNoise.Height);
            //for (var x = 0; x < pbNoise.Width; x++)
            //    for (var y = 0; y < pbNoise.Height; y++)
            //    {
            //        var n = _parent.ScriptedTerrainGenerator.GetValue(x, y);
            //        bmp.SetPixel(x, y, Colors[(int)n]);
            //    }
            //pbNoise.Image = bmp;
        }

        private void bCreateTerrain_Click(object sender, EventArgs e)
        {
            var sfd = new SaveFileDialog { Filter = "Lua Files|*.lua" };

            if (sfd.ShowDialog() == DialogResult.Cancel) return;

            File.WriteAllText(sfd.FileName, EmbeddedFiles.terrain);
            Process.Start(sfd.FileName);
            _parent.WatchTerrainScript(sfd.FileName);
            _parent.Title = $"{EmbeddedFiles.AppName} | {sfd.FileName}";
        }

        private void bOpenTerrain_Click(object sender, EventArgs e)
        {
            var ofd = new OpenFileDialog { Filter = "Lua Files|*.lua" };

            if (ofd.ShowDialog() == DialogResult.Cancel) return;

            _parent.WatchTerrainScript(ofd.FileName);
            _parent.Title = $"{EmbeddedFiles.AppName} | {ofd.FileName}";
        }

        private void bCancelGen_Click(object sender, EventArgs e)
        {
            _parent.CancelBackgroundTasks();
        }

        private void bManuallyGenerate_Click(object sender, EventArgs e)
        {
            _parent.ReRender(true);
        }

        private void cbPauseGen_CheckedChanged(object sender, EventArgs e)
        {
            bManuallyGenerate.Enabled = cbPauseGen.Checked;
        }

        private void pbMinColor_Click(object sender, EventArgs e)
        {
            if (colorPicker.ShowDialog() != DialogResult.OK)
                return;

            _parent.TintColor = colorPicker.Color;
            pbTerrainColor.BackColor = _parent.TintColor;
        }

        private void heightmapViewerToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new HeightmapViewer().Show();
        }

        private void cbVoxels_CheckedChanged(object sender, EventArgs e)
        {
            _parent.SetVoxels(cbVoxels.Checked);
        }
    }
}
